package com.ecommerce.demo.services;

import com.ecommerce.demo.Entities.User;
import com.ecommerce.demo.Exceptions.UserNotFoundException;
import com.ecommerce.demo.dtos.user.CreateUserRequestDto;
import com.ecommerce.demo.repositories.AddressRepository;
import com.ecommerce.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    // ✅ Create User
    public User createUser(CreateUserRequestDto createUserRequestDto) {
        User user = new User();
        user.setEmail(createUserRequestDto.getEmail());
        user.setUsername(createUserRequestDto.getUsername());
        user.setPassword(createUserRequestDto.getPassword()); // Store securely with encryption
        user.setPhone(createUserRequestDto.getPhone());
        user.setAddress(createUserRequestDto.getAddress());
        return userRepository.save(user);
    }

    // ✅ Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Get User by ID
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    // ✅ Update User
    public User updateUser(Long id, CreateUserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(userRequestDto.getEmail());
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword()); // Secure it properly
        user.setPhone(userRequestDto.getPhone());
        user.setAddress(userRequestDto.getAddress());
        return userRepository.save(user);
    }

    // ✅ Delete User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
