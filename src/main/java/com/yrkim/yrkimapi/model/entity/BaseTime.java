package com.yrkim.yrkimapi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
 * @MappedSuperclass : JPA Entity 클래스들이 BaseTime Entity를 상속하는 경우 시간 필드(createdDate, modifiedDate)도 컬럼으로 생성
 * - EntityListeners(AuditingEntityListener.class) : BaseTime Entiy 클래스에 Auditing 기능을 포함
 * - JPA Auditing : Spring Data JPA에서 제공하는 시간에 대해서만 자동으로 값을 넣어주는 기능이다
 * */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

    @Column(name = "created", updatable = false, nullable = false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Schema(description = "등록 시각")
    private LocalDateTime created;

    @Column(name = "modified", updatable = false, nullable = false)
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Schema(description = "수정 시각")
    private LocalDateTime modified;
}
