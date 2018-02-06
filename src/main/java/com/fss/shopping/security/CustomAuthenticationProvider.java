package com.fss.shopping.security;

import com.fss.shopping.persistence.dao.UserRepository;
import com.fss.shopping.persistence.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        LOGGER.info("Following user trying to login: " + auth.toString());
        LOGGER.info("Following user trying to login: " + auth.getName());
        LOGGER.info("Following user trying to login: " + auth.getDetails().toString());
        final User user = userRepository.findByEmail(auth.getName());
        if ((user == null)) {
            throw new BadCredentialsException("Invalid email or password");
        }

        final Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
