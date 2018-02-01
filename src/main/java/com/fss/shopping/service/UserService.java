package com.fss.shopping.service;

import com.fss.shopping.persistence.entity.User;
import com.fss.shopping.persistence.entity.VerificationToken;
import com.fss.shopping.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    User getUserByToken(String token);

    VerificationToken getVerificationToken(final String VerificationToken);

    VerificationToken.TokenState validateVerificationToken(String token);

    void createVerificationTokenForUser(User user, String token);
}
