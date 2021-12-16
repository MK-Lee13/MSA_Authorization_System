package com.server.user.users.domain;

import com.server.user.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static com.server.user.users.util.EncryptorSha512.encrypt;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_gen")
    @SequenceGenerator(name = "user_sequence_gen", sequenceName = "user_sequence")
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public User(
            Long id,
            String email,
            String password,
            String nickname,
            UserRole userRole
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this. nickname = nickname;
        this.userRole = userRole;
    }

    /**
     * 사용자 패스워드 비교 함수
     * @param comparePassword
     * @return
     */
    public boolean compare(String comparePassword) {
        if (password.equals(comparePassword)) {
            return true;
        }
        return false;
    }

}
