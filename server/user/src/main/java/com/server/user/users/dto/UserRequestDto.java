package com.server.user.users.dto;

import com.server.user.users.domain.User;
import com.server.user.users.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRequestDto {
    @Email(message = "email format is not valid. Please check your email.")
    private String email;

    @NotBlank(message = "password cannot be blank. Please check your password.")
    private String password;

    @NotBlank(message = "nickname cannot be blank. Please check your nickname.")
    private String nickname;

    public UserRequestDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User toEntity() {
        return new User(
                null,
                email,
                password,
                nickname,
                UserRole.ROLE_CLIENT
        );
    }
}
