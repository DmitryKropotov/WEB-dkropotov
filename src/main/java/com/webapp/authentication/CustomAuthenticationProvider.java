package com.webapp.authentication;

import com.webapp.controller.sessionModeControllers.SessionModeOffController;
import com.webapp.model.UserChecker;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    SessionModeOffController sessionModeOffController;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("MYYYYYYYYY LOG: This is authenticate in class CustomAuthenticationProvider");
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (sessionModeOffController.loginUserAndGetSessionId(new UserChecker(userName, password)).isPresent()) {
            List<GrantedAuthority> grantedAuths = new ArrayList();
            grantedAuths.add(()-> {return "AUTH_USER";});
            Authentication auth = new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
            log.info("MYYYYYYYYY LOG: " + auth.getAuthorities());
            return auth;
        }
        else {
            throw new AuthenticationCredentialsNotFoundException ("Invalid Credentials!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
