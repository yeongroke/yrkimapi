package com.yrkim.yrkimapi.model.entity;

import com.yrkim.yrkimapi.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@DynamicUpdate
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String title;

    @Column()
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "view_count")
    @ColumnDefault("0") // ddl로 default 설정
    private Long viewcount;

    @Transient
    public BoardDto toDto() {
        return new BoardDto(this);
    }
}
