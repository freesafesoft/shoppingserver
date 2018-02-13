package com.fss.shopping.service;

import com.fss.shopping.persistence.dao.RoleRepository;
import com.fss.shopping.persistence.dao.UserRepository;
import com.fss.shopping.persistence.dao.VerificationTokenRepository;
import com.fss.shopping.persistence.entity.Privilege;
import com.fss.shopping.persistence.entity.Role;
import com.fss.shopping.persistence.entity.User;
import com.fss.shopping.persistence.entity.VerificationToken;
import com.fss.shopping.security.LoginAttemptService;
import com.fss.shopping.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;
    private final HttpServletRequest request;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LoginAttemptService loginAttemptService, HttpServletRequest request,
                           RoleRepository roleRepository, VerificationTokenRepository tokenRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
        this.request = request;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserRegistrationDto registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setEnabled(false);
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_GUEST")));
        return userRepository.save(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        try {
            final User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true,
                    true, true, true, getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<>();
        final List<Privilege> collection = new ArrayList<>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
