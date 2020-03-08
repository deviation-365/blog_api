package com.deviation.blog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UsersDto {

    @Getter
    @Setter
    @ToString
    public static class AddRequestDto {
        private String email;
        private String name;
        private String password;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateRequestDto {
        private String password;
    }

    @Getter
    @Setter
    @ToString
    public static class ResponseDto {
        private String email;
        private String name;
    }
}
