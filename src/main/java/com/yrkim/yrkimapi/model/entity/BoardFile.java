package com.yrkim.yrkimapi.model.entity;

import com.yrkim.yrkimapi.model.dto.BoardFileDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@DynamicUpdate
@Table(name = "board_file")
@NoArgsConstructor
@AllArgsConstructor
public class BoardFile extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id",
            referencedColumnName = "id",
            nullable = false)
    private Board board;

    @Column(name = "origin_file_name",
            nullable = false,
            updatable = false)
    private String originFileName;

    @Column(name = "remote_file_name",
            nullable = false,
            updatable = false)
    private String remoteFileName;

    @Column(name = "filePath",
            nullable = false,
            updatable = false)
    private String filePath;

    @Column(name = "content_type",
            nullable = false,
            updatable = false)
    private String contentType;

    @Column(name = "original_file_extension",
            nullable = false,
            updatable = false)
    private String originalFileExtension;

    @Column(name = "file_Size",
            nullable = false,
            updatable = false)
    private Long fileSize;

    @Transient
    public BoardFileDto toDto() {
        return new BoardFileDto(this);
    }
}
