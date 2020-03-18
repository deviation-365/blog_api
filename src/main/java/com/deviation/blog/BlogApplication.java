package com.deviation.blog;

import com.deviation.blog.model.Users;
import com.deviation.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UsersRepository usersRepository,
                                               PasswordEncoder passwordEncoder) {
        return args -> {

            usersRepository.deleteAll();
            Users user = new Users();

            user.setEmail("hypemova@gmail.com");
            user.setName("sam");
            user.setPassword(passwordEncoder.encode("1111"));

            usersRepository.save(user);

        };
    }

}
