package com.deviation.blog.controller;

import com.deviation.blog.dto.ApiResponseDto;
import com.deviation.blog.dto.UsersDto;
import com.deviation.blog.model.Users;
import com.deviation.blog.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="${api.path.default}/auth")
public class AuthController {

    private final UsersService usersService;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponseDto<UsersDto.SignInUserInfo>> signIn(@Valid @RequestBody UsersDto.SignInInput signInInput) {

        UsersDto.SignInUserInfo info = usersService.signIn(signInInput);

        ApiResponseDto<UsersDto.SignInUserInfo> dto = new ApiResponseDto<>();
        dto.setSuccessStatus(info);
        return ResponseEntity.ok(dto);
    }
}
