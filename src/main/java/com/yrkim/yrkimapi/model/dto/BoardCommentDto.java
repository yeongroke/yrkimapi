package com.yrkim.yrkimapi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yrkim.yrkimapi.model.entity.BoardComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BoardCommentDto extends BaseTimeDto {
    @NotNull
    @Schema(description = "게시판 댓글 번호")
    private Long id;
    @NotNull
    @Schema(description = "게시판 댓글 내용")
    private String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long boardId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BoardDto board;

    public BoardCommentDto(BoardComment boardComment){
        this.id = boardComment.getId();
        this.content = boardComment.getContent();
        this.user = boardComment.getUser().toDto();
        this.board = boardComment.getBoard().toDto();
        setCreated(boardComment.getCreated());
        setModified(boardComment.getModified());
    }

    public BoardComment toEntity(){
        BoardComment boardComment = BoardComment.builder()
                .id(id)
                .content(content)
                .user(user.toEntity())
                .board(board.toEntity())
                .build();
        return boardComment;
    }
}
