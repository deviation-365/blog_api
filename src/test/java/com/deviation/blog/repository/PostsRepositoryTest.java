package com.deviation.blog.repository;

import com.deviation.blog.model.Posts;
import com.deviation.blog.model.Users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class PostsRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    @Rollback(value = false)
    public void createPost() {
        Users users = new Users();
        users.setName("염승민");
        users.setPassword("1234");
        users.setEmail("tmdals3021@gmail.com");


        Posts posts = new Posts();
        posts.setUsers(users);
        posts.setTitle("1");
        posts.setContent("11");

        postsRepository.save(posts);
        users.getPosts().forEach(posts1 -> System.out.println(posts1.getTitle()));


    }
}