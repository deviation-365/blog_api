package com.deviation.blog.controller;

import com.deviation.blog.model.Posts;
import com.deviation.blog.model.Users;
import com.deviation.blog.repository.PostsRepository;
import com.deviation.blog.repository.UsersRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "local")
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Before
    public void insertPost() {
        Users users = usersRepository.findByEmail("hypemova@gmail.com")
                .orElseThrow(EntityNotFoundException::new);
        Posts post = new Posts();
        post.setTitle("title");
        post.setContent("content");
        post.setUsers(users);
        postsRepository.save(post);
    }

    @After
    public void deletePost() {
        postsRepository.deleteAll();
    }

    @Test
    public void getPostList() throws Exception {

        mockMvc.perform(get("/v1/api/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(1)));
    }

    @Test
    public void getPostDetail() throws Exception {

        Users users = usersRepository.findByEmail("hypemova@gmail.com")
                .orElseThrow(EntityNotFoundException::new);
        Posts post = new Posts();
        post.setTitle("title2");
        post.setContent("content2");
        post.setUsers(users);
        postsRepository.save(post);

        Long id = postsRepository.save(post).getId();

        mockMvc.perform(get("/v1/api/posts/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", is("title2")));
    }


}