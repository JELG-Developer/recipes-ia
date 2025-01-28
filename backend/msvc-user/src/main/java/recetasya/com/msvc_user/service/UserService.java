package recetasya.com.msvc_user.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import recetasya.com.msvc_user.service.entities.User;
import recetasya.com.msvc_user.mapper.request.FindIdRequest;
import recetasya.com.msvc_user.mapper.response.UserResponse;
import recetasya.com.msvc_user.service.exception.UserException;
import recetasya.com.msvc_user.service.repository.UserRepository;

import lombok.extern.java.Log;

@Log
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
    
}