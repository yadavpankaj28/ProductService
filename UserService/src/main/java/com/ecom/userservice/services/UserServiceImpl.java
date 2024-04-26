package com.ecom.userservice.services;

import com.ecom.userservice.exceptions.UserDetailsException;
import com.ecom.userservice.models.Token;
import com.ecom.userservice.models.User;
import com.ecom.userservice.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signUp(User user) throws UserDetailsException {
        Optional<User> existingUserOptional = userRepository.getByEmail(user.getEmail());

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserDetailsException("User Email not passed");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserDetailsException("User Password not passed");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new UserDetailsException("Username not passed");
        }

        if (existingUserOptional.isPresent()) {
            throw new UserDetailsException("User Exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User createdUser = userRepository.save(user);
        hashedPassword = null;
        return createdUser;
    }

    @Override
    public Token login(String email, String password) {
        return null;
    }

    @Override
    public Boolean logout(String token) {
        return null;
    }
}
