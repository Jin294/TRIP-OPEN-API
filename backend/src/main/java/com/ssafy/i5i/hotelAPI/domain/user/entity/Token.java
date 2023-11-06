package com.ssafy.i5i.hotelAPI.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value = "token", timeToLive = 3600L)
@AllArgsConstructor
public class Token {
    @Id
    private String token;
    private Integer count;
}
