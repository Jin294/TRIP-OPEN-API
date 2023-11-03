package com.ssafy.i5i.hotelAPI.redis;

import com.ssafy.i5i.hotelAPI.domain.user.entity.Token;
import com.ssafy.i5i.hotelAPI.domain.user.service.TokenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TokenTest {
    @Autowired private TokenService tokenService;

    @Test
    void falseTokenValidTest() {
        boolean result = tokenService.checkValidToken("enj~~~~");
        assertThat(result).isEqualTo(false);
    }

    @Test
    void saveTokenAndValidTest() {
        tokenService.saveToken("enj");
        boolean result = tokenService.checkValidToken("enj");
        assertThat(result).isEqualTo(true);
        Token checkToken = tokenService.getTokenById("enj");
        assertThat(checkToken.getToken()).isEqualTo("enj");
        assertThat(checkToken.getCount()).isEqualTo(1);
    }

    @Test
    void updateCount() {
        tokenService.incrementTokenCount("enj");
        Token checkToken = tokenService.getTokenById("enj");
        assertThat(checkToken.getCount()).isEqualTo(2);
    }
}
