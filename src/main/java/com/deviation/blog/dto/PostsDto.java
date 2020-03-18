package com.deviation.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class PostsDto {

    @Getter @Setter
    public static class ResponseDto {

        private Long id;
        private String title;
        private String content;
        private UsersDto.ResponseDto users;
        private LocalDateTime created;

    }

    @Getter @Setter
    public static class ResponseDetailDto {
        private Long id;
        private String title;
        private String content;
        private UsersDto.ResponseDto users;
        private CommentsDto.ResponseDto comments;
        private LocalDateTime created;
    }

    @Getter @Setter
    public static class AddRequestDto {
        private String title;
        private String content;
    }

    @Getter @Setter
    public static class UpdateRequestDto {
        private String title;
        private String content;
    }

}
