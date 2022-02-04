package com.yrkim.yrkimapi.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
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
    @CreatedDate()
    @ApiModelProperty(value = "등록 시각")
    private LocalDateTime created;

    @LastModifiedDate
    @ApiModelProperty(value = "수정 시각")
    private LocalDateTime modified;
}
