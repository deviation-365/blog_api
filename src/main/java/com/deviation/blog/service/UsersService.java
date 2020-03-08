package com.deviation.blog.service;

import com.deviation.blog.common.ApiResultStatus;
import com.deviation.blog.common.BusinessException;
import com.deviation.blog.config.jwt.JwtTokenProvider;
import com.deviation.blog.dto.UsersDto;
import com.deviation.blog.model.Users;
import com.deviation.blog.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    private final JwtTokenProvider jwtTokenProvider;

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

    public UsersDto.SignInUserInfo signIn(UsersDto.SignInInput signInInput) {
        Users signInUsers = usersRepository.findByEmail(signInInput.getEmail())
                .map(users -> {
                    // 패스워드 체크
                    if (!passwordEncoder.matches(signInInput.getPassword(), users.getPassword())) {
                        throw new BusinessException(ApiResultStatus.LOGIN_FAILED);
                    }
                    return users;
                })
                .orElseThrow(() -> new EntityNotFoundException(ApiResultStatus.LOGIN_FAILED.getMessage()));

        UsersDto.SignInUserInfo info = modelMapper.map(signInUsers, UsersDto.SignInUserInfo.class);

        String token = jwtTokenProvider.createToken(info);

        log.info("[token]" + token);
        info.setToken(token);

        return info;
    }
}
