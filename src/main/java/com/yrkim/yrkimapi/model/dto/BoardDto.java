package com.yrkim.yrkimapi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yrkim.yrkimapi.model.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class BoardDto extends BaseTimeDto {

    @NotNull
    @Schema(description = "게시판 번호")
    private Long id;

    @NotNull
    @Schema(description = "게시판 타이틀")
    private String title;

    @NotNull
    @Schema(description = "게시판 내용")
    private String content;

    @NotNull
    @Schema(description = "작성자")
    private UserDto user;

    @Schema(description = "조회수")
    private Long viewcount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BoardCommentDto boardComment;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<BoardFileDto> boardFiles;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<String> boardFileUrls = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MultipartFile[] files;

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
        board.setCreated(getCreated());
        board.setModified(getModified());
        return board;
    }
}
