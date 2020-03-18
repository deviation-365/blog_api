package com.deviation.blog.repository;

import com.deviation.blog.model.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "local")
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Rollback(value = false)
    public void createUser() {
        Users users = new Users();

        users.setName("염승민");
        users.setPassword("1234");
        users.setEmail("tmdals3021@gmail.com");
        usersRepository.save(users);

        assertThat(users.getId(), is(users.getId()));
        assertThat(users.getName(), is("염승민"));
        assertThat(users.getPassword(), is("1234"));
    }
}