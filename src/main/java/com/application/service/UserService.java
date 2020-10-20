package com.application.service;

import com.application.model.User;
import com.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){ return userRepository.save(user); }
    public Collection<User> findAll(){ return userRepository.findAll(); }

}
