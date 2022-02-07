package com.yrkim.yrkimapi.service;

import com.yrkim.yrkimapi.model.response.CommonResponse;
import com.yrkim.yrkimapi.model.response.CommonResult;
import com.yrkim.yrkimapi.model.response.ListResult;
import com.yrkim.yrkimapi.model.response.SingleResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CommonResponseService {

    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setResult(data);
        setSuccessResult(result);
        return result;
    }

    public <T> ListResult<T> getListResult(Page<T> pageObj) {
        ListResult<T> result = new ListResult<>();
        result.setListData(pageObj.getContent());
        result.setTotalElements(pageObj.getTotalElements());
        result.setTotalPages(pageObj.getTotalPages());
        result.setNowPage(pageObj.getNumber() + 1);
        result.setPageLimit(pageObj.getSize());
        setSuccessResult(result);
        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    private void setSuccessResult(CommonResult result) {
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMessage());
    }
}
