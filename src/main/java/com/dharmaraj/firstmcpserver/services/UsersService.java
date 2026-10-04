package com.dharmaraj.firstmcpserver.services;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import com.dharmaraj.firstmcpserver.models.Users;
import com.dharmaraj.firstmcpserver.repositories.UsersRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class UsersService {

    private final UsersRepository usersRepository;

    @Tool(name = "addUser", description = "Adds a new user to the system.")
    public Users addUser(@ToolParam String name, @ToolParam String email, @ToolParam String phone) {

        if (usersRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with email " + email + " already exists.");
        }

        Users user = new Users(name, email, phone);
        return usersRepository.save(user);
    }

    @Tool(name = "getAllUsers", description = "Retrieves all users from the system.")
    public List<Users> getAllUsers() {

        return usersRepository.findAll();
    }
}
