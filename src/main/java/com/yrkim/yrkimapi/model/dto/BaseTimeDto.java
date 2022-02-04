package com.yrkim.yrkimapi.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseTimeDto {
    private LocalDateTime created;
    private LocalDateTime modified;
}
