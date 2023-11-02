package com.ssafy.i5i.hotelAPI.domain.user.service;

import com.ssafy.i5i.hotelAPI.domain.user.entity.User;
import com.ssafy.i5i.hotelAPI.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;

    public boolean checkValidToken(String token) {
        Optional<User> user = userRepository.findByToken(token);

        if(user.isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }
}
