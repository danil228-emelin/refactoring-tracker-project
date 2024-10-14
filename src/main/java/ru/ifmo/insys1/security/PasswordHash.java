package ru.ifmo.insys1.security;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.codec.digest.DigestUtils;

@ApplicationScoped
public class PasswordHash {

    public String hash(String password) {
        return DigestUtils.sha512Hex(password);
    }

    public boolean verify(String password, String hash) {
        return hash.equals(hash(password));
    }
}
