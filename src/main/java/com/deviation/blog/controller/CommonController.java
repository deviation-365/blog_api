package com.deviation.blog.controller;

import com.deviation.blog.common.ApiResultStatus;
import com.deviation.blog.dto.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommonController {

    @GetMapping("/error")
    protected ResponseEntity<ApiResponseDto<?>> error() {

        ApiResponseDto<?> errorResponse = new ApiResponseDto<>();
        errorResponse.setCode(ApiResultStatus.TOKEN_INVALID.getCode());
        errorResponse.setMessage(ApiResultStatus.TOKEN_INVALID.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/error/401")
    protected ResponseEntity<ApiResponseDto<?>> error401(@RequestParam(value = "code") String errorCode) {

        log.error("[errorCode]" + errorCode);

        ApiResponseDto<?> errorResponse = new ApiResponseDto<>();
        errorResponse.setCode(errorCode);
        errorResponse.setMessage(ApiResultStatus.messageOfCode(errorCode));

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
