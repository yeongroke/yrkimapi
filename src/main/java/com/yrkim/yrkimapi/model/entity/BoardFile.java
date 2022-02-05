package com.yrkim.yrkimapi.model.entity;

import com.yrkim.yrkimapi.model.dto.BoardFileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "remote_file_name")
    private String remoteFileName;

    @Column(name = "filePath")
    private String filePath;

    @Transient
    public BoardFileDto toDto() {
        return new BoardFileDto(this);
    }
}
