package com.server.user.users.controller;


import com.server.user.users.dto.AuthRequestDto;
import com.server.user.users.dto.UserRequestDto;
import com.server.user.users.dto.AuthResponseDto;
import com.server.user.users.dto.UserResponseDto;
import com.server.user.users.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(name = "/api/v1/users")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserController {
    private final UserService userService;

    /**
     * 테스트 용 라우트 메소드
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.ok().body("info");
    }

    /**
     * 사용자 확인 API
     * @return
     */
    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getInfo(@PathVariable String email) {
        return ResponseEntity.ok()
                .body(userService.getUserByEmail(email));
    }

    /**
     * 회원가입
     * @param userRequestDto
     * @return
     */
    @PostMapping("/sign/up")
    public ResponseEntity<Void> singUp(@Valid @RequestBody UserRequestDto userRequestDto) {
        Long id = userService.signUp(userRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/users/" + id))
                .build();
    }

    /**
     * 로그인
     * @param authRequestDto
     * @return
     */
    @PostMapping("/sign/in")
    public ResponseEntity<AuthResponseDto> signIn(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok()
                .body(userService.signIn(authRequestDto));
    }
}
