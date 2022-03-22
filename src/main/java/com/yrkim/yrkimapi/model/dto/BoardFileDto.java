package com.yrkim.yrkimapi.model.dto;

import com.yrkim.yrkimapi.model.entity.BoardFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardFileDto extends BaseTimeDto{

    @Schema(description = "게시판파일 번호")
    private Long id;

    @Schema(description = "게시판 정보")
    private BoardDto board;

    @Schema(description = "파일 원본 이름")
    private String originFileName;

    @Schema(description = "파일 업로드 이름")
    private String remoteFileName;

    @Schema(description = "파일 경로")
    private String filePath;

    @Schema(description = "파일 경로")
    private String contentType;

    @Schema(description = "파일 경로")
    private String originalFileExtension;

    @Schema(description = "파일 경로")
    private Long fileSize;
    
    public BoardFileDto(BoardFile boardFile) {
        this.id = boardFile.getId();
        this.board = boardFile.getBoard().toDto();
        this.originFileName = boardFile.getOriginFileName();
        this.remoteFileName = boardFile.getRemoteFileName();
        this.filePath = boardFile.getFilePath();
        this.contentType = boardFile.getContentType();
        this.originalFileExtension = boardFile.getOriginalFileExtension();
        this.fileSize = boardFile.getFileSize();
        setCreated(boardFile.getCreated());
        setModified(boardFile.getModified());
    }

    public BoardFile toEntity() {
        BoardFile boardFile = BoardFile.builder()
                .id(this.id)
                .board(this.board.toEntity())
                .originFileName(this.originFileName)
                .remoteFileName(this.remoteFileName)
                .filePath(this.filePath)
                .contentType(this.contentType)
                .originalFileExtension(this.originalFileExtension)
                .fileSize(this.fileSize)
                .build();

        return boardFile;
    }
}
