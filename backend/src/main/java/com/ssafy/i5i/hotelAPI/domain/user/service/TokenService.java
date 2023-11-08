package com.ssafy.i5i.hotelAPI.domain.user.service;

import com.ssafy.i5i.hotelAPI.domain.user.entity.Token;
import com.ssafy.i5i.hotelAPI.domain.user.entity.User;
import com.ssafy.i5i.hotelAPI.domain.user.repository.TokenRedisRepository;
import com.ssafy.i5i.hotelAPI.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRedisRepository tokenRedisRepository;
    private final UserRepository userRepository;
    @Transactional(readOnly = true)
    public synchronized boolean checkValidToken(String tokenId) {
        Token tokenFromRedis = tokenRedisRepository.findById(tokenId).orElse(null);
        if (tokenFromRedis == null) {
            Optional<User> user = userRepository.findByToken(tokenId);
            if (!user.isPresent()) return false;
            saveToken(user.get().getToken());
            incrementTokenCount(user.get().getToken());
            return true;
        }
        log.info("TokenService 22 lines, token = {}", tokenFromRedis.getToken());
        log.info("TokenService 23 lines, count = {}", tokenFromRedis.getCount());
        if (tokenFromRedis.getCount() < 100) {
            incrementTokenCount(tokenFromRedis.getToken());
            return true;
        }
        return false;
    }

    public boolean incrementTokenCount(String tokenId) {
        Token token = tokenRedisRepository.findById(tokenId).orElse(null);
        if (token != null) {
            token.setCount(token.getCount() + 1);
            tokenRedisRepository.save(token);
            return true;
        }
        return false;
    }
    public boolean saveToken(String tokenId) {
        Token token = new Token(tokenId, 0);
        tokenRedisRepository.save(token);
        return true;
    }
}
