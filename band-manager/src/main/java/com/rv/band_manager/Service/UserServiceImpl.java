package com.rv.band_manager.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.rv.band_manager.Model.User;
import com.rv.band_manager.Repository.UserRepository;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

@Service
public class UserServiceImpl{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) throws Exception {
        System.out.println(user.getUsername());
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new Exception("Name already exists: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
