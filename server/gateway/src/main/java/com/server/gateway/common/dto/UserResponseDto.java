package com.server.gateway.common.dto;

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
}
