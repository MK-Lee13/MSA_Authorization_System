package com.server.user.users.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/v1/users")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserController {

    /**
     * 테스트 용 라우트 메소드
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.ok().body("info");
    }
}
