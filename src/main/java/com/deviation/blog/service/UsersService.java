package com.deviation.blog.service;

import com.deviation.blog.model.Users;
import com.deviation.blog.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public Page<Users> getUserList(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    public Users addUsers(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersRepository.save(users);
    }

    @Transactional
    public Users updateUsers(Long userId, String password) {
        Users users = usersRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        users.setPassword(passwordEncoder.encode(password));
        return users;
    }
}
