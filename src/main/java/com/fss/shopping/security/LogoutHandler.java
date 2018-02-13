package com.fss.shopping.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component("logoutHandler")
public class LogoutHandler implements LogoutSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LOGGER.info("User logout " + authentication.getName());
        final HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute("user");
        }
    }
}
