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
    @Transactional
    public boolean checkValidToken(String tokenId) {
        synchronized (this) {
            Token tokenFromRedis = tokenRedisRepository.findById(tokenId).orElse(null);
            if (tokenFromRedis == null) {
                Optional<User> user = userRepository.findByToken(tokenId);
                if (!user.isPresent()) return false;
                saveToken(user.get().getToken());
                return true;
            }
            log.info("TokenService 22 lines, token = {}", tokenFromRedis.getToken());
            log.info("TokenService 23 lines, count = {}", tokenFromRedis.getCount());
            if (tokenFromRedis.getCount() < 100000) return true;
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Token getTokenById(String tokenId) {
        return tokenRedisRepository.findById(tokenId).orElse(null);
    }

    @Transactional
    public boolean incrementTokenCount(String tokenId) {
        Token token = tokenRedisRepository.findById(tokenId).orElse(null);
        if (token != null) {
            token.setCount(token.getCount() + 1);
            tokenRedisRepository.save(token);
            return true;
        }
        return false;
    }
    @Transactional
    public boolean saveToken(String tokenId) {
        Token token = new Token(tokenId, 0);
        tokenRedisRepository.save(token);
        return true;
    }
    @Transactional(readOnly = true)
    public boolean maxCheck(String tokenId) {
        Token token = tokenRedisRepository.findById(tokenId).orElse(null);
        if(token != null && token.getCount() >= 3) {
            return false;
        }
        return true;
    }
}
