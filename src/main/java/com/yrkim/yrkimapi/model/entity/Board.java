package com.yrkim.yrkimapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yrkim.yrkimapi.model.dto.BoardCommentDto;
import com.yrkim.yrkimapi.model.dto.BoardDto;
import com.yrkim.yrkimapi.model.dto.BoardFileDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@EntityListeners({AuditingEntityListener.class})
@DynamicUpdate
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",
            nullable = false,
            updatable = false)
    private String title;

    @Column(name = "content",
            nullable = false,
            updatable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "view_count",
            nullable = false,
            updatable = false)
    @ColumnDefault("0") // ddl로 default 설정
    private Long viewcount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardFile> boardFiles = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardComment> boardComments = new ArrayList<>();

    public BoardDto toDto() {
        return BoardDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .user(user.toDto())
                .viewcount(viewcount)
                .build();
    }

    public void setBoardComments(Collection<BoardCommentDto> boardCommentDtos) {
        this.boardComments = boardCommentDtos.stream().map(boardCommentDto -> {
            return boardCommentDto.toEntity();
        }).collect(Collectors.toList());
    }

    public void setBoardFiles(Collection<BoardFileDto> boardFileDtos) {
        this.boardFiles = boardFileDtos.stream().map(boardFileDto -> {
            return boardFileDto.toEntity();
        }).collect(Collectors.toList());
    }
}
