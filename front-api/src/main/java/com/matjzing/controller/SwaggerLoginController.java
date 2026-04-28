package com.matjzing.controller;

import com.matjzing.dto.common.ResponseModel;
import com.matjzing.dto.swagger.SwaggerLoginRequest;
import com.matjzing.service.SwaggerUserService;
import com.matjzing.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Slf4j
@Controller
@RequiredArgsConstructor
public class SwaggerLoginController {

    private final SwaggerUserService service;

    @GetMapping("/view/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ResponseModel<Object>> login(@RequestBody SwaggerLoginRequest req) {
        String login = service.login(req);
        if (StringUtils.hasText(login)) {
            return RestUtil.error("ERR_SWAGGERLOGIN_001", "일치하는 회원 정보가 없는 경우");
        } else {
            return RestUtil.ok(login);
        }
    }

}
