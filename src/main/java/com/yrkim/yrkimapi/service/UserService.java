package com.yrkim.yrkimapi.service;

import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.model.response.ListResult;
import com.yrkim.yrkimapi.model.response.SingleResult;
import com.yrkim.yrkimapi.payload.request.LoginRequest;
import com.yrkim.yrkimapi.payload.request.SignupRequest;
import org.springframework.data.domain.Pageable;


public interface UserService {
    SingleResult<UserDto> save(SignupRequest user);
    SingleResult<UserDto> getUser(long id);
    ListResult<UserDto> getUsers(Pageable pageable);
    CommonResult delete(long id);
    String signIn(LoginRequest user);
}
