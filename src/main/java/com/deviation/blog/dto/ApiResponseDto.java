package com.deviation.blog.dto;

import com.deviation.blog.common.ApiResultStatus;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponseDto<T> {
    public String code;
    public String message;
    public T data;

    public void setSuccessStatus(@Nullable T data) {

        this.code = ApiResultStatus.REQUEST_SUCCESS.getCode();
        this.message = ApiResultStatus.REQUEST_SUCCESS.getMessage();
        this.data = data;
    }
}
