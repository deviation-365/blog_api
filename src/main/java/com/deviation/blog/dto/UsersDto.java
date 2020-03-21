package com.deviation.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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

    @Getter
    @Setter
    @ToString
    public static class SignInInput {

        private String email;
        private String password;
    }

    @Getter @Setter
    @ToString
    public static class SignInUserInfo implements Serializable {

        @JsonIgnore
        private Long id;    // sequence number

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;    // admin Name

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String email;

        @JsonIgnore
        private String password;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String token;

    }

}
