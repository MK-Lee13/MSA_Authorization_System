package com.server.user.users.dto;

import com.server.user.users.domain.User;
import com.server.user.users.domain.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String userRole;

    public UserResponseDto(
            String email,
            String userRole
    ) {
        this.email = email;
        this.userRole = userRole;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(
                user.getEmail(),
                user.getUserRole().name()
        );
    }
}
