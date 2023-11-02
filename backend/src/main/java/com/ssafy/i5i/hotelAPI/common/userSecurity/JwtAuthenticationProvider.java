package com.ssafy.i5i.hotelAPI.common.userSecurity;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName();
        String password = (String)authentication.getCredentials();

        ServiceProvider
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
