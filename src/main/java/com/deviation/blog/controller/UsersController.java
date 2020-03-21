package com.deviation.blog.controller;

import com.deviation.blog.dto.ApiResponseDto;
import com.deviation.blog.dto.UsersDto;
import com.deviation.blog.model.Users;
import com.deviation.blog.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="${api.path.default}/users")
public class UsersController {

    private final ModelMapper modelMapper;

    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<UsersDto.ResponseDto>>> getUsersList(@RequestParam Integer page,
                                                                                   @RequestParam Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UsersDto.ResponseDto> responseDto = usersService.getUserList(pageable)
                .map(users -> modelMapper.map(users, UsersDto.ResponseDto.class));

        ApiResponseDto<Page<UsersDto.ResponseDto>> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setSuccessStatus(responseDto);
        return ResponseEntity.ok(apiResponseDto);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<UsersDto.ResponseDto>> addUsers(@RequestBody UsersDto.AddRequestDto addUsersDto) {

        Users users = modelMapper.map(addUsersDto, Users.class);

        Users savedUsers = usersService.addUsers(users);

        UsersDto.ResponseDto responseDto = modelMapper.map(savedUsers, UsersDto.ResponseDto.class);

        ApiResponseDto<UsersDto.ResponseDto> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setSuccessStatus(responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponseDto);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<ApiResponseDto<UsersDto.ResponseDto>> updateUsers(@PathVariable Long userId,
                                                                            @RequestBody UsersDto.UpdateRequestDto updateRequestDto) {

        Users users = usersService.updateUsers(userId, updateRequestDto.getPassword());

        UsersDto.ResponseDto responseDto = modelMapper.map(users, UsersDto.ResponseDto.class);

        ApiResponseDto<UsersDto.ResponseDto> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setSuccessStatus(responseDto);

        return ResponseEntity.ok(apiResponseDto);
    }
}
