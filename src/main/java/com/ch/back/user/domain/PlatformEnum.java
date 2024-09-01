package com.ch.back.user.domain;

public enum PlatformEnum {

    KAKAO("kakao"), GOOGLE("google"), NAVER("naver");

    private final String platform;
    PlatformEnum(String platform) {
        this.platform = platform;
    }

}
