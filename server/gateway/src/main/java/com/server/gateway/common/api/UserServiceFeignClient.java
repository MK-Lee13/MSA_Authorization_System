package com.server.gateway.common.api;

import com.server.gateway.common.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {
    @GetMapping("/user-service/api/v1/users/{email}")
    UserResponseDto getUser(@PathVariable String email);
}
