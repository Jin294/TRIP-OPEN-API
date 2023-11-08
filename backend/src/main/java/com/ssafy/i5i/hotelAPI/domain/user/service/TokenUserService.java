package com.ssafy.i5i.hotelAPI.domain.user.service;

import com.ssafy.i5i.hotelAPI.domain.user.entity.TokenUser;
import com.ssafy.i5i.hotelAPI.domain.user.entity.User;
import com.ssafy.i5i.hotelAPI.domain.user.repository.TokenUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenUserService {
    private final TokenUserRepository tokenUserRepository;

    @Transactional
    public boolean checkValidToken(String token) {
        TokenUser user = tokenUserRepository.findByToken(token).orElse(null);
        if(user == null || user.getCount() >= 100000) {
            return false;
        }
        tokenUserRepository.updateCount(token);
        return true;
    }
}
