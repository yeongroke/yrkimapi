package com.yrkim.yrkimapi.service;

import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.model.entity.User;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.model.response.ListResult;
import com.yrkim.yrkimapi.model.response.SingleResult;
import com.yrkim.yrkimapi.payload.request.LoginRequest;
import com.yrkim.yrkimapi.payload.request.SignupRequest;
import org.springframework.data.domain.Pageable;


public interface UserService {
    SingleResult<User> save(SignupRequest user);
    SingleResult<User> getUser(long id);
    ListResult<User> getUsers(Pageable pageable);
    CommonResult delete(long id);
    String signIn(LoginRequest user);
}
