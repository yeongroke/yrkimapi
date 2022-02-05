package com.yrkim.yrkimapi.service.impl;

import com.yrkim.yrkimapi.repository.jpa.UserRepository;
import com.yrkim.yrkimapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


}
