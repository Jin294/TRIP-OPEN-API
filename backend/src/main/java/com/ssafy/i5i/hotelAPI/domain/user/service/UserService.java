package com.ssafy.i5i.hotelAPI.domain.user.service;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.user.entity.User;
import com.ssafy.i5i.hotelAPI.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById (String id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> {throw new CommonException(ExceptionType.NULL_POINT_EXCEPTION);});
        return user;
    }
}
