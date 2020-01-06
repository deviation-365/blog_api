package com.deviation.blog.repository;

import com.deviation.blog.model.Post;
import com.deviation.blog.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @Rollback(value = false)
    public void createPost() {
        User user = new User();
        user.setName("염승민");
        user.setPassword("1234");
        user.setEmail("tmdals3021@gmail.com");


        Post post = new Post();
        post.setUser(user);
        post.setTitle("1");
        post.setContent("11");

        postRepository.save(post);
        user.getPosts().forEach(post1 -> System.out.println(post1.getTitle()));


    }
}