package com.ssafy.i5i.hotelAPI.domain.user.service;

import com.ssafy.i5i.hotelAPI.domain.user.entity.Token;
import com.ssafy.i5i.hotelAPI.domain.user.entity.User;
import com.ssafy.i5i.hotelAPI.domain.user.repository.TokenRedisRepository;
import com.ssafy.i5i.hotelAPI.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRedisRepository tokenRedisRepository;
    @Transactional(readOnly = true)
    public boolean checkValidToken(String tokenId) {
        Token token = tokenRedisRepository.findById(tokenId).orElse(null);
        return token != null;
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
