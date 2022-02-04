package com.yrkim.yrkimapi.model.dto;

import com.yrkim.yrkimapi.model.entity.BoardFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BoardFileDto extends BaseTimeDto{

    @NotNull
    @ApiModelProperty(value = "게시판파일 번호")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "게시판 정보")
    private BoardDto board;

    @NotNull
    @ApiModelProperty(value = "파일 원본 이름")
    private String originFileName;

    @NotNull
    @ApiModelProperty(value = "파일 업로드 이름")
    private String remoteFileName;

    public BoardFileDto(BoardFile boardFile) {
        this.id = boardFile.getId();
        this.board = boardFile.getBoard().toDto();
        this.originFileName = boardFile.getOriginFileName();
        this.remoteFileName = boardFile.getRemoteFileName();
        setCreated(boardFile.getCreated());
        setModified(boardFile.getModified());
    }

    public BoardFile toEntity() {
        BoardFile boardFile = BoardFile.builder()
                .id(id)
                .board(board.toEntity())
                .originFileName(originFileName)
                .remoteFileName(remoteFileName)
                .build();

        return boardFile;
    }
}
