package com.ch.back.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@Getter
@Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "platform")
    @Enumerated(value = EnumType.STRING)
    private PlatformEnum platformEnum;

    @Column(name = "platform_id")
    private Long platformId;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(Long platformId, String username, String password, PlatformEnum platformEnum) {
        // 소셜 로그인 용
        this.platformId = platformId;
        this.role = UserRoleEnum.USER;
        this.username = username;
        this.password = password;
        this.platformEnum = platformEnum;
    }


}
