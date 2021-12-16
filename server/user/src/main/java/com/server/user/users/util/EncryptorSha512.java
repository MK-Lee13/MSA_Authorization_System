package com.server.user.users.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class EncryptorSha512 {
    public static String encrypt(String password) {
        String encryptPassword = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.reset();
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            encryptPassword = String.format("%0128x",
                    new BigInteger(1, messageDigest.digest())
            );
        } catch(Exception e) {
             // TODO : 임시 로직 추후에 Exception 처리 필요
            e.printStackTrace();
        }
        return encryptPassword;
    }
}
