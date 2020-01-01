package com.deviation.blog.repository;

import com.deviation.blog.model.User;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void createUser() {
        User user = new User();

        user.setName("염승민");
        user.setPassword("1234");
        user.setEmail("tmdals3021@gmail.com");
        userRepository.save(user);

        assertThat(user.getId(), is(user.getId()));
        assertThat(user.getName(), is("염승민"));
        assertThat(user.getPassword(), is("1234"));
    }
}