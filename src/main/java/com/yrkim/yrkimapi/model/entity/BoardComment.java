package com.yrkim.yrkimapi.model.entity;

import com.yrkim.yrkimapi.model.dto.BoardCommentDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@DynamicUpdate
@Table(name = "board_comment")
@NoArgsConstructor
@AllArgsConstructor
public class BoardComment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content",
            nullable = false,
            updatable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id",
            referencedColumnName = "id",
            nullable = false)
    private Board board;

    @Transient
    public BoardCommentDto toDto() {
        return new BoardCommentDto(this);
    }
}
