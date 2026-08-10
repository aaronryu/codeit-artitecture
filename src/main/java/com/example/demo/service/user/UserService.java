package com.example.demo.service.user;

import com.example.demo.repository.user.User;
import com.example.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        Optional<User> wrappedUser = userRepository.findById(id);
                 User         user = wrappedUser
                         .orElseThrow(() -> new RuntimeException("찾으시는 유저가 존재하지 않습니다"));
        return user;
    }

    public Optional<User> findUser(Integer id) {
        return userRepository.findById(id);
    }


    public User create(User entity) {
        Optional<User> wrappedCreated = userRepository.create(entity);
                 User         created = wrappedCreated
                         .orElseThrow(() -> new RuntimeException("유저가 정상적으로 생성되지 않습니다"));
        return created;
    }

    public User update(User entity) {
        Optional<User> wrappedUser = userRepository.update(entity);
                 User         user = wrappedUser
                         .orElseThrow(() -> new RuntimeException("업데이트가 정상적으로 되지 않습니다"));
        return user;
    }

    public void active(Integer id) {
        User exist = this.getUser(id);
        exist.active();
        userRepository.update(exist);
    }

    public void softDelete(Integer id) {
        User exist = this.getUser(id);
        exist.delete();
        userRepository.update(exist);
    }

    public void hardDelete(Integer id) {
        User exist = this.getUser(id);
        userRepository.delete(id);
    }
}
