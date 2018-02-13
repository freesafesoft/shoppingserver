package com.fss.shopping.web.controller;

import com.fss.shopping.web.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/user/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse userPing() {
        LOGGER.info("/user/ping");
        return new BaseResponse();
    }

    @RequestMapping(value = "/user/read", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse userReadCheck() {
        LOGGER.info("/user/read");
        return new BaseResponse();
    }

    @RequestMapping(value = "/user/write", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse userWriteCheck() {
        LOGGER.info("/user/write");
        return new BaseResponse();
    }

    @RequestMapping(value = "/user/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse userPermissions() {
        LOGGER.info("userPing");
        // TODO get permissions from DB
        return new BaseResponse();
    }
}
