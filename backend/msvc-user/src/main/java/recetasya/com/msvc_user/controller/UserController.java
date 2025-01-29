package recetasya.com.msvc_user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import recetasya.com.msvc_user.mapper.request.*;
import recetasya.com.msvc_user.mapper.response.*;
import recetasya.com.msvc_user.service.UserService;
import recetasya.com.msvc_user.service.exception.UserException;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @Operation(summary = "all users")
    @ApiResponse(responseCode = "200", description = "all users")
    public ResponseEntity <List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/save")
    @Operation(summary = "save user")
    @ApiResponse(responseCode = "200", description = "Save user",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserRequest.class)))
    @ApiResponse(responseCode = "400", description = "Username or email already exists")
    public ResponseEntity <StandardResponse> saveUser(@RequestBody CreateUserRequest request) throws UserException{        
        return ResponseEntity.ok(userService.save(request));
    }

    @PutMapping("/update")
    @Operation(summary = "update user")
    @ApiResponse(responseCode = "200", description = "update user",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUserRequest.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity <StandardResponse> updateUser(@RequestBody UpdateUserRequest request) throws UserException{        
        return ResponseEntity.ok(userService.update(request));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete user")
    @ApiResponse(responseCode = "200", description = "delete user",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindIdRequest.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity <StandardResponse> deleteUser(@RequestBody FindIdRequest request) throws UserException{        
        return ResponseEntity.ok(userService.delete(request));
    }

}
