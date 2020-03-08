package com.deviation.blog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResultStatus {

    REQUEST_SUCCESS("0000", "정상적으로 처리되었습니다."),
    LOGIN_FAILED("4000", "아이디, 비밀번호를 확인해주세요."),

    TOKEN_NOT_FOUND("4101", "토큰을 찾을수 없음."),
    TOKEN_DATE_EXPIRED("4102", "토큰 유효기간 만료."),
    TOKEN_INVALID("4103", "유효하지 않은 토큰."),
    TOKEN_CREATED_FAILED("4104", "토큰 생성 Error, 다시 시도해 주세요.");

    private String code;
    private String message;

    public static String  messageOfCode(String code) {
        ApiResultStatus[] var1 = values();
        int enumLength = var1.length;

        for (int var3 = 0; var3 < enumLength; ++var3) {
            ApiResultStatus resultStatus = var1[var3];
            if (resultStatus.code.equals(code)) {
                return resultStatus.getMessage();
            }
        }

        throw new IllegalArgumentException("No matching message for [" + code + "]");
    }
}
