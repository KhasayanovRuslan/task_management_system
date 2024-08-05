package com.xcompany.taskmanagementsystem.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.xcompany.taskmanagementsystem.api.dto.UserDto;
import com.xcompany.taskmanagementsystem.api.exception.NotFoundException;
import com.xcompany.taskmanagementsystem.api.model.User;
import com.xcompany.taskmanagementsystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    public User createUser(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserDto updatedUserDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        if (existingUser != null) {
            String updatedName = updatedUserDto.getUsername();
            if (updatedName != null && !updatedName.isBlank()) {
                existingUser.setUsername(updatedName);
            }

            String updatedPassword = updatedUserDto.getPassword();
            if (updatedPassword != null && !updatedPassword.isBlank()) {
                existingUser.setPassword(updatedPassword);
            }

            return userRepository.save(existingUser);
        }
        return null;
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
