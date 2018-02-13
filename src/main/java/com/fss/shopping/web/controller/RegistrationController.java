package com.fss.shopping.web.controller;

import com.fss.shopping.persistence.dao.RoleRepository;
import com.fss.shopping.persistence.dao.UserRepository;
import com.fss.shopping.persistence.dao.VerificationTokenRepository;
import com.fss.shopping.persistence.entity.Role;
import com.fss.shopping.persistence.entity.User;
import com.fss.shopping.persistence.entity.VerificationToken;
import com.fss.shopping.registration.OnRegistrationCompleteEvent;
import com.fss.shopping.service.UserService;
import com.fss.shopping.utils.CaptchaVerification;
import com.fss.shopping.web.BaseResponse;
import com.fss.shopping.web.dto.UserRegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class RegistrationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public RegistrationController(ApplicationEventPublisher eventPublisher, UserService userService, UserRepository userRepository,
                                  VerificationTokenRepository tokenRepository, RoleRepository roleRepository) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse registerUserJson(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                         HttpServletRequest request) {
        LOGGER.info("registration");
        User existing = userService.findUserByEmail(userDto.getEmail());

        if (existing != null) {
            return new BaseResponse(199, "User already exists");
        }

        String captcha = request.getParameter("g-recaptcha-response");
        LOGGER.info("Recaptcha: " + captcha);
        String ip = request.getRemoteAddr();
        boolean verify = CaptchaVerification.verify(captcha, ip);
        if (!verify)
            return new BaseResponse("Bad recaptcha: " + captcha);

        User user = userService.save(userDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
        LOGGER.info("Registration complete: " + user.toString());
        return new BaseResponse();
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public BaseResponse confirmRegistration(@RequestParam("token") final String token) {
        LOGGER.info("Confirmation registration");
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            LOGGER.info("There is no such token in DB: " + token);
            return new BaseResponse(199, "Verification token is not valid or doesn't exists");
        }

        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            LOGGER.info("Token expired: " + token);
            return new BaseResponse(199, "Verification token expired");
        }
        final User user = verificationToken.getUser();
        Role userRole = roleRepository.findByName("ROLE_USER");
        LOGGER.info("User: " + user.toString());
        LOGGER.info("New role: " + userRole.toString());
        LOGGER.info("Repository: " + roleRepository.toString());
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);
        user.setEnabled(true);

        userRepository.save(user);
        // TODO publish confirmed event
        LOGGER.info("Registration confirmed: " + user.toString());
        return new BaseResponse();
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
