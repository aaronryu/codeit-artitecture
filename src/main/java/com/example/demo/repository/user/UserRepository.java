package com.example.demo.repository.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final static Map<Integer, User> USERS = new HashMap<>();
}
