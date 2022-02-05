package com.yrkim.yrkimapi.model.dto;

import com.yrkim.yrkimapi.model.entity.BoardFile;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BoardFileDto extends BaseTimeDto{

    @NotNull
    @Schema(description = "게시판파일 번호")
    private Long id;

    @NotNull
    @Schema(description = "게시판 정보")
    private BoardDto board;

    @NotNull
    @Schema(description = "파일 원본 이름")
    private String originFileName;

    @NotNull
    @Schema(description = "파일 업로드 이름")
    private String remoteFileName;

    @NotNull
    @Schema(description = "파일 경로")
    private String filePath;
    
    public BoardFileDto(BoardFile boardFile) {
        this.id = boardFile.getId();
        this.board = boardFile.getBoard().toDto();
        this.originFileName = boardFile.getOriginFileName();
        this.remoteFileName = boardFile.getRemoteFileName();
        this.filePath = boardFile.getFilePath();
        setCreated(boardFile.getCreated());
        setModified(boardFile.getModified());
    }

    public BoardFile toEntity() {
        BoardFile boardFile = BoardFile.builder()
                .id(id)
                .board(board.toEntity())
                .originFileName(originFileName)
                .remoteFileName(remoteFileName)
                .filePath(filePath)
                .build();

        return boardFile;
    }
}
