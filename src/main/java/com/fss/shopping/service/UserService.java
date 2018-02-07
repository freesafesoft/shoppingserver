package com.fss.shopping.service;

import com.fss.shopping.persistence.entity.User;
import com.fss.shopping.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findUserByEmail(String email);

    User save(UserRegistrationDto registration);

    void createVerificationTokenForUser(User user, String token);
}
