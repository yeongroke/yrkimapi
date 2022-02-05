package com.yrkim.yrkimapi.controller;

import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.service.CommonResponseService;
import com.yrkim.yrkimapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final CommonResponseService commonResponseService;

    @PostMapping(path = "/sign-in" , produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CommonResult> signIn(@RequestBody UserDto user, HttpServletResponse res) {



        return ResponseEntity.ok(commonResponseService.getSuccessResult());
    }

}
