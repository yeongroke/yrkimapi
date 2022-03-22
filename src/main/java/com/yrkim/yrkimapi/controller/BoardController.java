package com.yrkim.yrkimapi.controller;

import com.yrkim.yrkimapi.model.dto.BoardDto;
import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.model.response.SingleResult;
import com.yrkim.yrkimapi.security.CustomUserDetails;
import com.yrkim.yrkimapi.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Tag(name = "BoardController", description = "board controller")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/{id}")
    public ResponseEntity<SingleResult<BoardDto>> boardFindById(@PathVariable long id) {

        return ResponseEntity.ok(new SingleResult<>());
    }

    /*
    * 우선은 로컬에 파일 업로드 다음 repository에는 서버에 업로드 예정
    * */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult boardSave(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  @ModelAttribute BoardDto boardDto) throws Exception {
        return boardService.save(boardDto);
    }
}
