package com.ssafy.i5i.hotelAPI.domain.user.service;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.user.entity.User;
import com.ssafy.i5i.hotelAPI.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public boolean isValidToken(String tokenId) {
        User user = userRepository.findByToken(tokenId).orElse(null);
        log.info("UserService 19 lines, user = {}", user.getToken());
        if(user == null){
            return false;
        }
        return true;
    }

    public User getUserById (String id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> {throw new CommonException(ExceptionType.NULL_POINT_EXCEPTION);});
        return user;
    }


    //회원가입

    //로그인

    //로그아웃

    //API용 토근 발급

    //회원삭제

    //아이디찾기

    //비밀번호 수정 ( 이메일 )
}
