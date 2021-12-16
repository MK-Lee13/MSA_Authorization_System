package com.server.user.users.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.server.user.users.util.EncryptorSha512.encrypt;

@Getter
@Setter
public class AuthRequestDto {
    @Email(message = "email format is not valid. Please check your email.")
    private String email;

    @NotBlank(message = "password cannot be blank. Please check your password.")
    private String password;

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = encrypt(password);
    }
}
