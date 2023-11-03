package com.ssafy.i5i.hotelAPI.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@Setter
@RedisHash(value = "token", timeToLive = 24L)
@AllArgsConstructor
public class Token {
    @Id
    private String id;
    @Indexed
    private String token;
    private Integer count;
}
