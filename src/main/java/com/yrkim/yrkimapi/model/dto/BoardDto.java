package com.yrkim.yrkimapi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yrkim.yrkimapi.model.entity.Board;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BoardDto extends BaseTimeDto {

    @NotNull
    @ApiModelProperty(value = "게시판 번호")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "게시판 타이틀")
    private String title;

    @NotNull
    @ApiModelProperty(value = "게시판 내용")
    private String content;

    @NotNull
    @ApiModelProperty(value = "작성자")
    private UserDto user;

    @NotNull
    @ApiModelProperty(value = "조회수")
    private Long viewcount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BoardFileDto boardFile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BoardCommentDto boardComment;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = board.getUser().toDto();
        this.viewcount = board.getViewcount();
        setCreated(board.getCreated());
        setModified(board.getModified());
    }

    public Board toEntity() {
        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .user(user.toEntity())
                .viewcount(viewcount)
                .build();
        return board;
    }
}
