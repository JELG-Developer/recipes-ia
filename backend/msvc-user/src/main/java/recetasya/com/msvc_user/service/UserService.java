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

    public StandardResponse saveUser(CreateUserRequest request) throws UserException {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserException("Username already exists", 400);
        }
    
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists", 400);
        }

        User user = modelMapper.map(request, User.class);

        Optional<Role> userRoleOptional = roleRepository.findByName("Usuario");
        Role userRole;
        if (userRoleOptional.isPresent()) {
            userRole = userRoleOptional.get();
        } else {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole.setDescription("Default role for new users");
            roleRepository.save(userRole);
        }

        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);        

        return new StandardResponse(200, "User created", user.getId());
    }

    public StandardResponse saveAdmin(CreateUserRequest request) throws UserException {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserException("Username already exists", 400);
        }
    
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists", 400);
        }

        User user = modelMapper.map(request, User.class);

        Optional<Role> userRoleOptional = roleRepository.findByName("Usuario");
        Role userRole;
        if (userRoleOptional.isPresent()) {
            userRole = userRoleOptional.get();
        } else {
            userRole = new Role();
            userRole.setName("ROLE_ADMIN");
            userRole.setDescription("Default role for new admins");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            roleRepository.save(userRole);
        }

        user.setRole(userRole);
        userRepository.save(user);        

        return new StandardResponse(200, "User created", user.getId());
    }

    public StandardResponse update(UpdateUserRequest request) throws UserException {
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new UserException("User not found", 404));
        modelMapper.map(request, user);
        userRepository.save(user);
        return new StandardResponse(200, "User updated", user.getId());
    }

    public StandardResponse delete(FindIdRequest request) throws UserException {
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new UserException("User not found", 404));
        userRepository.delete(user);
        return new StandardResponse(200, "User deleted", user.getId());
    }
}