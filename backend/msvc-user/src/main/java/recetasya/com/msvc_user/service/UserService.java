package recetasya.com.msvc_user.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import recetasya.com.msvc_user.service.entities.*;
import recetasya.com.msvc_user.mapper.request.*;
import recetasya.com.msvc_user.mapper.response.*;
import recetasya.com.msvc_user.service.exception.*;
import recetasya.com.msvc_user.service.repository.*;

import lombok.extern.java.Log;

import org.modelmapper.*;

@Log
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    public List<UserResponse> getAll() {
        log.info("Getting all users");
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponse::fromUser).collect(Collectors.toList());
    }

    public UserResponse getById(FindIdRequest request) throws UserException {
        log.info("Getting user by id");
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new UserException("User not found", 404));
        return UserResponse.fromUser(user);
    }

    public StandardResponse createUser(CreateUserRequest request) throws UserException {
    
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists", 400);
        }
    
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_USER");
                    newRole.setDescription("Default role for new users");
                    return roleRepository.save(newRole);
                });
    
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    
        return new StandardResponse(200, "User created");
    }
    
    public StandardResponse createAdmin(CreateUserRequest request) throws UserException {
    
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists", 400);
        }
    
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_ADMIN");
                    newRole.setDescription("Default role for new admins");
                    return roleRepository.save(newRole);
                });
    
        user.setRoles(Collections.singleton(adminRole));
        userRepository.save(user);
    
        return new StandardResponse(200, "Admin created");
    }

    public UserResponse getByMail(FindMailRequest request) throws UserException {
        log.info("Getting user by username");
        User user = userRepository.findByEmail(request.getMail()).orElseThrow(() -> new UserException("User not found", 404));
        return UserResponse.fromUser(user);
    }

    public StandardResponse update(UpdateUserRequest request) throws UserException {
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new UserException("User not found", 404));
        modelMapper.map(request, user);
        userRepository.save(user);
        return new StandardResponse(200, "User updated");
    }

    public StandardResponse delete(FindIdRequest request) throws UserException {
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new UserException("User not found", 404));
        userRepository.delete(user);
        return new StandardResponse(200, "User deleted");
    }
}