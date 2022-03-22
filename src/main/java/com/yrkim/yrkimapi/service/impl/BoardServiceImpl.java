package com.yrkim.yrkimapi.service.impl;

import com.yrkim.yrkimapi.exception.BoardNotFoundException;
import com.yrkim.yrkimapi.model.dto.BoardDto;
import com.yrkim.yrkimapi.model.dto.BoardFileDto;
import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.model.entity.Board;
import com.yrkim.yrkimapi.model.entity.BoardFile;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.model.response.ListResult;
import com.yrkim.yrkimapi.model.response.SingleResult;
import com.yrkim.yrkimapi.repository.jpa.BoardRepository;
import com.yrkim.yrkimapi.security.CustomUserDetails;
import com.yrkim.yrkimapi.service.BoardService;
import com.yrkim.yrkimapi.service.CommonResponseService;
import com.yrkim.yrkimapi.utils.MultiFIleUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final CommonResponseService commonResponseService;
    private final MultiFIleUploadUtil multiFIleUploadUtil;

    @Override
    public SingleResult<BoardDto> getBoard(long id) {
        BoardDto boardDto = boardRepository.findById(id)
                .map(board -> {
                    return board.toDto();
                }).orElseThrow(BoardNotFoundException::new);
        return commonResponseService.getSingleResult(boardDto);
    }

    @Override
    public ListResult<BoardDto> getBoards(Pageable pageable) {
        List<BoardDto> boardDtoList = boardRepository.findAll(pageable)
                .stream()
                .map(board -> {
                    return board.toDto();
                }).collect(Collectors.toList());
        return commonResponseService.getListResult(new PageImpl<>(boardDtoList,
                pageable,
                boardRepository.findAll(pageable).getTotalElements()));
    }

    @Override
    public SingleResult<BoardDto> save(BoardDto boardDto, CustomUserDetails customUserDetails) throws Exception {
        Board board = boardDto.toEntity();
        boardDto.setUser(UserDto.builder().username(customUserDetails.getUsername()).build());
        board.setBoardFiles(multiFIleUploadUtil.toBoardFile(boardDto.getFiles() , boardDto));

        return null;
    }

    @Override
    public SingleResult<BoardDto> modify(BoardDto board) {
        return null;
    }

    @Override
    public CommonResult delete(long id) throws Exception {
        return null;
    }
}
