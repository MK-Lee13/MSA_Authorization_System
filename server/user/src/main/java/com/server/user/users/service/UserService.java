package com.server.user.users.service;

import com.server.user.common.security.JwtTokenProvider;
import com.server.user.users.domain.User;
import com.server.user.users.dto.AuthRequestDto;
import com.server.user.users.dto.UserRequestDto;
import com.server.user.users.dto.AuthResponseDto;
import com.server.user.users.dto.UserResponseDto;
import com.server.user.users.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        Optional<User> isUser = userRepository.findByEmail(email);
        /**
         * TODO : 나중에 해당 부분 Exception 제작
         */
        User user = isUser.get();
        return UserResponseDto.of(user);
    }

    @Transactional(readOnly = true)
    public AuthResponseDto signIn(AuthRequestDto authRequestDto) {
        Optional<User> isUser = userRepository.findByEmailAndPassword(authRequestDto.getEmail(), authRequestDto.getPassword());
        /**
         * TODO : 나중에 해당 부분 Exception 제작
         */
        User user = isUser.get(); // TODO : 나중에 지울코드
        return new AuthResponseDto(
                jwtTokenProvider.createAccessToken(user.getEmail(), user.getUserRole()),
                jwtTokenProvider.createRefreshToken()
        );
    }

    @Transactional
    public Long signUp(UserRequestDto userRequestDto) {
        User user = userRepository.save(userRequestDto
                .toEntity()
        );
        return user.getId();
    }
}
