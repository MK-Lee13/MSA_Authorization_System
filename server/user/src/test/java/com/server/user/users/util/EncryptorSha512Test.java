package com.server.user.users.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptorSha512Test {

    @DisplayName(value = "SHA512_암호화_비교_성공")
    @ParameterizedTest(name = "SHA512_암호화_비교_성공 테스트 [{index}] => `{argumentsWithNames}`")
    @ValueSource(strings = {"asdasdasd", "qweqweqwe", "zxczxczxcz", "asdgghah", "gnfglkjhkslt", "123adfczdxcvf", "qwebvxcbcx"})
    void SHA512_암호화_비교_성공(String password) {
        String originalEncrypt = EncryptorSha512.encrypt(password);
        String compareEncrypt = EncryptorSha512.encrypt(password);
        assertEquals(originalEncrypt, compareEncrypt);
    }
}