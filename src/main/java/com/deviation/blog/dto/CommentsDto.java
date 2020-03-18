package com.deviation.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CommentsDto {

    @Getter @Setter
    public static class ResponseDto {

        private Long id;
        private String content;
        private UsersDto.ResponseDto users;
        private LocalDateTime created;

    }
}
