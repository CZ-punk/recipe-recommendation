package com.ch.back.entity;

public enum PlatformEnum {

    KAKAO("kakao"), GOOGLE("google"), NAVER("naver");

    private final String platform;
    PlatformEnum(String platform) {
        this.platform = platform;
    }

}
