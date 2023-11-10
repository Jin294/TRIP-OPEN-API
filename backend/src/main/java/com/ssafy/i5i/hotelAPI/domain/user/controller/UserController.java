package com.ssafy.i5i.hotelAPI.domain.user.controller;

import com.ssafy.i5i.hotelAPI.common.response.CommonResponse;
import com.ssafy.i5i.hotelAPI.common.response.DataResponse;
import com.ssafy.i5i.hotelAPI.domain.user.dto.UserDto;
import com.ssafy.i5i.hotelAPI.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PutMapping("/token")
    public DataResponse<?> updateToken(Principal principal) {
        log.info("UserController 23 lines, id = {}", principal.getName());
        UserDto.UserInfo userInfo = userService.updateApiToken(principal.getName());
        return new DataResponse<UserDto.UserInfo>(200, "success", userInfo);
    }

    @PostMapping("/service/login")
    public DataResponse<?> login(@RequestBody UserDto.LoginDto loginDto) {
        Map<String, String> data = userService.login(loginDto);
        log.info(data.get("access_token"));
        return new DataResponse<Map<String, String>>(200, "success", data);
    }

    @PostMapping("/service/login/signup")
    public CommonResponse login(@RequestBody UserDto.SignUp signUp) {
        userService.signUp(signUp);
        return new CommonResponse(200, "success");
    }

    @DeleteMapping("/user")
    public CommonResponse login(Principal principal) {
        userService.deleteUser(principal.getName());
        return new CommonResponse(200, "success");
    }
}
