package ru.ifmo.insys1.security;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

@ApplicationScoped
@Slf4j
public class PasswordHash {

    public String hash(String password) {
        log.warn("Hashing password: {}", password);

        return DigestUtils.sha512Hex(password);
    }

    public boolean verify(String password, String hash) {
        log.warn("Verifying password: {}, with hash: {}", password, hash);

        return hash.equals(hash(password));
    }
}
