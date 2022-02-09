package com.yrkim.yrkimapi.service;

import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.model.entity.User;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.model.response.ListResult;
import com.yrkim.yrkimapi.model.response.SingleResult;
import org.springframework.data.domain.Pageable;


public interface UserService {
    SingleResult<User> save(UserDto user);
    SingleResult<User> getUser(long id);
    ListResult<User> getUsers(Pageable pageable);
    CommonResult delete(long id);
}
