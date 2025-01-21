package com.ecommerce.demo.controllers;

import com.ecommerce.demo.Exceptions.UserNotFoundException;
import com.ecommerce.demo.dtos.user.CreateUserRequestDto;
import com.ecommerce.demo.dtos.user.UserResponseDto;
import com.ecommerce.demo.Entities.User;
import com.ecommerce.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        User user = userService.createUser(createUserRequestDto);
        UserResponseDto responseDto = mapToUserResponseDto(user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        UserResponseDto responseDto = mapToUserResponseDto(user);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> responseDtos = users.stream()
                .map(this::mapToUserResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtos,HttpStatus.OK);
    }

    private UserResponseDto mapToUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
