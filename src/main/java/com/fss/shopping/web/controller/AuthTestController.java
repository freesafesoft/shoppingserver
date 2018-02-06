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
public class AuthTestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTestController.class);

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse get() {
        LOGGER.info("getProduct");
        return new BaseResponse("OK");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse add() {
        LOGGER.info("addProduct");
        return new BaseResponse("OK");
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse check() {
        LOGGER.info("check");
        return new BaseResponse("OK");
    }
}