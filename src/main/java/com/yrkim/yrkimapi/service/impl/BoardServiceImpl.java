package com.yrkim.yrkimapi.service.impl;

import com.yrkim.yrkimapi.repository.jpa.BoardRepository;
import com.yrkim.yrkimapi.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
}
