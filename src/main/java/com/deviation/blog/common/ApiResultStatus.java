package com.deviation.blog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResultStatus {

    REQUEST_SUCCESS("0000", "정상적으로 처리되었습니다.");
    private String code;
    private String message;
}
