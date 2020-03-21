package com.deviation.blog.service;

import com.deviation.blog.model.Posts;
import com.deviation.blog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Page<Posts> getPostList(Pageable pageable) {

        return postsRepository.findAll(pageable);
    }

    public Posts getPostDetail(Long postId) {

        return postsRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Posts registerPost(Posts post) {
        return postsRepository.save(post);
    }
}
