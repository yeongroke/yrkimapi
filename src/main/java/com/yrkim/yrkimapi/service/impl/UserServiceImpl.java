package com.yrkim.yrkimapi.service.impl;

import com.yrkim.yrkimapi.exception.UserNotFoundException;
import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.model.entity.User;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.model.response.ListResult;
import com.yrkim.yrkimapi.model.response.SingleResult;
import com.yrkim.yrkimapi.payload.request.LoginRequest;
import com.yrkim.yrkimapi.payload.request.SignupRequest;
import com.yrkim.yrkimapi.repository.jpa.UserRepository;
import com.yrkim.yrkimapi.security.JwtTokenUtil;
import com.yrkim.yrkimapi.service.CommonResponseService;
import com.yrkim.yrkimapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CommonResponseService commonResponseService;

    @Override
    public SingleResult<UserDto> save(SignupRequest user) {
        UserDto userDto = new UserDto(user);
        return commonResponseService.getSingleResult(new UserDto(userRepository.save(userDto.toEntity())));
    }

    @Override
    public SingleResult<UserDto> getUser(long id) {
        return commonResponseService.getSingleResult(new UserDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new)));
    }

    @Override
    public ListResult<UserDto> getUsers(Pageable pageable) {
        List<UserDto> userDtoList = userRepository.findAll(pageable)
                .stream()
                .map(user -> {
                    return new UserDto(user);
                }).collect(Collectors.toList());
        return commonResponseService.getListResult(new PageImpl<>(userDtoList,
                pageable,
                userRepository.findAll(pageable).getTotalElements()));
    }

    @Override
    public CommonResult delete(long id) {
        userRepository.deleteById(id);
        return commonResponseService.getSuccessResult();
    }

    @Override
    public String signIn(LoginRequest user) {
        commonResponseService.getSingleResult(userRepository.findByUsername(user.getUsername())
                .orElseThrow(UserNotFoundException::new));
        String jwt = jwtTokenUtil.generateToken(user.getUsername());
        return jwt;
    }
}
