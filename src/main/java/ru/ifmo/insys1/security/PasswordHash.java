package ru.ifmo.insys1.security;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
@Slf4j
public class PasswordHash {

    // Константы для определения алгоритма хэширования
    private static final String BCRYPT_PREFIX = "bcrypt:";
    private static final String SHA512_PREFIX = "sha512:";

    // Стоимость bcrypt (от 4 до 31, рекомендуемые значения 10-14)
    private static final int BCRYPT_COST = 12;

    // Флаг для отладки (в production установите false)
    private boolean debugLogging = false;

    @PostConstruct
    public void init() {
        log.info("PasswordHash initialized with bcrypt cost factor: {}", BCRYPT_COST);
    }

    /**
     * Хэширует пароль с использованием bcrypt
     * @param password Пароль в открытом виде
     * @return Хэш пароля с префиксом алгоритма
     */
    public String hash(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (debugLogging) {
            log.debug("Hashing password with bcrypt (cost: {})", BCRYPT_COST);
        }

        // Генерируем соль и хэшируем пароль
        String salt = BCrypt.gensalt(BCRYPT_COST);
        String hashedPassword = BCrypt.hashpw(password, salt);

        // Добавляем префикс алгоритма для будущей миграции
        return BCRYPT_PREFIX + hashedPassword;
    }

    /**
     * Проверяет пароль против хранимого хэша
     * Поддерживает как bcrypt, так и устаревший SHA512
     * @param password Пароль для проверки
     * @param storedHash Хранимый хэш (может быть с префиксом алгоритма)
     * @return true если пароль верный
     */
    public boolean verify(String password, String storedHash) {
        if (password == null || storedHash == null) {
            throw new IllegalArgumentException("Password and hash cannot be null");
        }

        if (debugLogging) {
            log.debug("Verifying password against hash (length: {})", storedHash.length());
        }

        try {
            // Определяем алгоритм по префиксу
            if (storedHash.startsWith(BCRYPT_PREFIX)) {
                // Bcrypt хэш
                String hashWithoutPrefix = storedHash.substring(BCRYPT_PREFIX.length());
                return BCrypt.checkpw(password, hashWithoutPrefix);

            } else if (storedHash.startsWith(SHA512_PREFIX)) {
                // Устаревший SHA512 хэш (для обратной совместимости)
                String hashWithoutPrefix = storedHash.substring(SHA512_PREFIX.length());
                String sha512Hash = org.apache.commons.codec.digest.DigestUtils.sha512Hex(password);
                boolean isValid = hashWithoutPrefix.equals(sha512Hash);

                if (isValid && debugLogging) {
                    log.warn("Legacy SHA512 hash verified. Consider migrating to bcrypt.");
                }
                return isValid;

            } else {
                // Хэш без префикса - предполагаем bcrypt (для обратной совместимости)
                return BCrypt.checkpw(password, storedHash);
            }

        } catch (Exception e) {
            log.error("Error verifying password: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Проверяет, нуждается ли хэш в обновлении (устаревший алгоритм или низкий cost factor)
     * @param storedHash Хранимый хэш
     * @return true если рекомендуется перехэшировать
     */
    public boolean needsRehash(String storedHash) {
        if (storedHash == null || storedHash.trim().isEmpty()) {
            return true;
        }

        // Если это SHA512 - точно нужно обновить
        if (storedHash.startsWith(SHA512_PREFIX)) {
            return true;
        }

        // Если это bcrypt без префикса или со старым cost factor
        try {
            if (storedHash.startsWith(BCRYPT_PREFIX)) {
                storedHash = storedHash.substring(BCRYPT_PREFIX.length());
            }

            // Извлекаем cost factor из bcrypt хэша
            String[] parts = storedHash.split("\\$");
            if (parts.length >= 4) {
                String costStr = parts[2];
                int currentCost = Integer.parseInt(costStr);

                // Если cost factor ниже рекомендуемого
                return currentCost < BCRYPT_COST;
            }
        } catch (Exception e) {
            log.warn("Cannot parse hash for rehash check: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Мигрирует устаревший хэш в bcrypt
     * @param password Пароль в открытом виде
     * @param legacyHash Устаревший хэш (SHA512)
     * @return Новый bcrypt хэш
     * @throws IllegalArgumentException если пароль не соответствует хэшу
     */
    public String migrateToBcrypt(String password, String legacyHash) {
        if (!verify(password, legacyHash)) {
            throw new IllegalArgumentException("Password does not match the legacy hash");
        }

        log.info("Migrating legacy hash to bcrypt");
        return hash(password);
    }

    /**
     * Генерирует случайный безопасный пароль
     * @param length Длина пароля (минимум 8)
     * @return Случайный пароль
     */
    public String generateRandomPassword(int length) {
        if (length < 8) {
            length = 12; // Минимальная безопасная длина
        }

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();

        java.security.SecureRandom random = new java.security.SecureRandom();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    /**
     * Проверяет сложность пароля
     * @param password Пароль для проверки
     * @return true если пароль соответствует минимальным требованиям безопасности
     */
    public boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
            if ("!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(c) >= 0) hasSpecial = true;
        }

        // Минимум 3 из 4 критериев
        int criteriaMet = 0;
        if (hasUpper) criteriaMet++;
        if (hasLower) criteriaMet++;
        if (hasDigit) criteriaMet++;
        if (hasSpecial) criteriaMet++;

        return criteriaMet >= 3 && password.length() >= 8;
    }

    /**
     * Устанавливает уровень логирования
     * @param enabled true для включения подробного логирования (только для разработки!)
     */
    public void setDebugLogging(boolean enabled) {
        this.debugLogging = enabled;
        if (enabled) {
            log.warn("DEBUG LOGGING ENABLED FOR PASSWORDHASH - DISABLE IN PRODUCTION!");
        }
    }

    /**
     * Получает текущий cost factor bcrypt
     */
    public int getBcryptCost() {
        return BCRYPT_COST;
    }
}