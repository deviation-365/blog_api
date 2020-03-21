package com.deviation.blog.controller;

import com.deviation.blog.dto.ApiResponseDto;
import com.deviation.blog.dto.PostsDto;
import com.deviation.blog.model.Posts;
import com.deviation.blog.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="${api.path.default}/posts")
public class PostController {

    private final ModelMapper modelMapper;
    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<PostsDto.ResponseDto>>> getPostList(@RequestParam(defaultValue = "1") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "id");
        Page<Posts> postList = postsService.getPostList(pageable);
        Page<PostsDto.ResponseDto> responseDto = postList.map(post -> modelMapper.map(post, PostsDto.ResponseDto.class));

        ApiResponseDto<Page<PostsDto.ResponseDto>> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setData(responseDto);
        return ResponseEntity.ok(apiResponseDto);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<PostsDto.ResponseDetailDto>> getPostDetail(@PathVariable Long postId) {

        Posts postDetail = postsService.getPostDetail(postId);

        PostsDto.ResponseDetailDto dto = modelMapper.map(postDetail, PostsDto.ResponseDetailDto.class);

        ApiResponseDto<PostsDto.ResponseDetailDto> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setData(dto);

        return ResponseEntity.ok(apiResponseDto);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<PostsDto.ResponseDetailDto>> registerPost(
                    @Valid @RequestBody PostsDto.AddRequestDto requestDto) {

        Posts post = modelMapper.map(requestDto, Posts.class);

        Posts savedPost = postsService.registerPost(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{postId}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        PostsDto.ResponseDetailDto responseDto = modelMapper.map(savedPost, PostsDto.ResponseDetailDto.class);

        ApiResponseDto<PostsDto.ResponseDetailDto> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setData(responseDto);

        return ResponseEntity.created(location).body(apiResponseDto);
    }

}
