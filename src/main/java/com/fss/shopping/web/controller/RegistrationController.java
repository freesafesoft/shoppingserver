package com.fss.shopping.web.controller;

import com.fss.shopping.persistence.dao.UserRepository;
import com.fss.shopping.persistence.dao.VerificationTokenRepository;
import com.fss.shopping.persistence.entity.User;
import com.fss.shopping.persistence.entity.VerificationToken;
import com.fss.shopping.registration.OnRegistrationCompleteEvent;
import com.fss.shopping.service.UserService;
import com.fss.shopping.utils.VerifyRecaptcha;
import com.fss.shopping.web.BaseResponse;
import com.fss.shopping.web.dto.UserRegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;

@Controller
public class RegistrationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        LOGGER.info("Get Request");
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse registerUserJson(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                            HttpServletRequest request) {
        return registerUser(userDto, request);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public BaseResponse registerUserXml(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                            HttpServletRequest request) {

        return registerUser(userDto, request);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public BaseResponse registerUserStream(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                        HttpServletRequest request) {
        return registerUser(userDto, request);
    }

    private BaseResponse registerUser(UserRegistrationDto userDto, HttpServletRequest request) {
        LOGGER.info("registration");
        User existing = userService.findByEmail(userDto.getEmail());

        if (existing != null) {
            return new BaseResponse("User already exists");
        }
        String captcha = request.getParameter("g-recaptcha-response");
        LOGGER.info("Recaptcha: " + captcha);
        String ip = request.getRemoteAddr();
        try {
            boolean verify = VerifyRecaptcha.verify(captcha, ip);
            if (verify == false)
                return new BaseResponse("Bad recaptcha: " + captcha);
        } catch (IOException e) {
            return new BaseResponse("Bad recaptcha: " + captcha);
        }
        User user = userService.save(userDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
        LOGGER.info("Registration complete: " + user.toString());
        return new BaseResponse("OK");
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public BaseResponse confirmRegistration(@RequestParam("token") final String token) {
        LOGGER.info("Confirmation registration");
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            LOGGER.info("There is no such token in DB: " + token);
            return new BaseResponse("Verification token is not valid or doesn't exists");
        }

        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            LOGGER.info("Token expired: " + token);
            return new BaseResponse("Verification token expired");
        }
        final User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        // TODO publish confirmed event
        LOGGER.info("Registration confirmed: " + user.toString());
        return new BaseResponse("OK");
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
