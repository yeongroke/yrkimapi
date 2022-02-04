package com.yrkim.yrkimapi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yrkim.yrkimapi.model.entity.BoardComment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardCommentDto extends BaseTimeDto {
    private Long id;
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
