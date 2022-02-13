package com.yrkim.yrkimapi.controller;

import com.yrkim.yrkimapi.exception.ParameterValidException;
import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.payload.request.LoginRequest;
import com.yrkim.yrkimapi.payload.request.SignupRequest;
import com.yrkim.yrkimapi.security.JwtTokenUtil;
import com.yrkim.yrkimapi.service.CommonResponseService;
import com.yrkim.yrkimapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    private final UserService userService;
    private final CommonResponseService commonResponseService;

    @PostMapping(path = "/sign-in" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequest user, BindingResult result) {
        if(result.hasErrors()) {
            throw new ParameterValidException("Parameter Check");
        }
        log.info("signup : {}" , String.format("%s",user.toString()));

        return ResponseEntity.ok(userService.signIn(user));
    }

    @PostMapping(path = "/sign-up" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult> signUp(@RequestBody @Valid SignupRequest user, BindingResult result) throws ParameterValidException {
        if(result.hasErrors()) {
            throw new ParameterValidException("Parameter Check");
        }
        log.info("signup : {}" , String.format("%s",user.toString()));
        userService.save(user);

        return ResponseEntity.ok(commonResponseService.getSuccessResult());
    }
}
