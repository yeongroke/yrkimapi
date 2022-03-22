package com.yrkim.yrkimapi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yrkim.yrkimapi.model.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardDto extends BaseTimeDto {

    @Schema(description = "게시판 번호")
    private Long id;

    @NotNull
    @Schema(description = "게시판 타이틀")
    private String title;

    @NotNull
    @Schema(description = "게시판 내용")
    private String content;

    @Schema(description = "작성자")
    private UserDto user;

    @Schema(description = "조회수")
    private Long viewcount;

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
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .user(this.user.toEntity())
                .viewcount(this.viewcount)
                .build();


        board.setCreated(getCreated());
        board.setModified(getModified());
        return board;
    }

    /*public void setBoardFile() {
        this.bo
    }*/
}
