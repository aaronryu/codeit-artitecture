package com.example.demo.service.user;

import com.example.demo.repository.user.User;
import com.example.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(Integer id) {
        Optional<User> wrappedUser = userRepository.findById(id);
                 User         user = wrappedUser
                         .orElseThrow(() -> new RuntimeException("찾으시는 유저가 존재하지 않습니다"));
        return user;
    }

    public Optional<User> findUser(Integer id) {
        return userRepository.findById(id);
    }
}
