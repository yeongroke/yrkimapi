package com.yrkim.yrkimapi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommonResult {

    @ApiModelProperty(value = "응답 코드 번호")
    private int code;
    @ApiModelProperty(value = "응답 메시지")
    private String msg;
}
