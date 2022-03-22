package com.yrkim.yrkimapi.service;

import com.yrkim.yrkimapi.model.dto.BoardDto;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.model.response.ListResult;
import com.yrkim.yrkimapi.model.response.SingleResult;
import com.yrkim.yrkimapi.security.CustomUserDetails;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    SingleResult<BoardDto> getBoard(long id);
    ListResult<BoardDto> getBoards(Pageable pageable);
    SingleResult<BoardDto> save(BoardDto board, CustomUserDetails customUserDetails) throws Exception;
    SingleResult<BoardDto> modify(BoardDto board);
    CommonResult delete(long id) throws Exception;
}
