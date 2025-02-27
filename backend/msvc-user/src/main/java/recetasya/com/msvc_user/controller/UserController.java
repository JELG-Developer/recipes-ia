package recetasya.com.msvc_user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import recetasya.com.msvc_user.mapper.request.*;
import recetasya.com.msvc_user.mapper.response.*;
import recetasya.com.msvc_user.service.UserService;
import recetasya.com.msvc_user.service.exception.UserException;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Manage user data")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @Operation(summary = "all users")
    @ApiResponse(responseCode = "200", description = "all users")
    public ResponseEntity <List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/find")
    @Operation(summary = "find user by id")
    @ApiResponse(responseCode = "200", description = "Find user by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindIdRequest.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity <UserResponse> findUserById(@RequestBody FindIdRequest request) throws UserException{        
        return ResponseEntity.ok(userService.getById(request));
    }

    @PostMapping("/find/mail")
    @Operation(summary = "find user by mail")
    @ApiResponse(responseCode = "200", description = "Find user by mail",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindEmailRequest.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity <EmailResponse> findUserByMail(@RequestBody FindEmailRequest request) throws UserException{        
        return ResponseEntity.ok(userService.getByMail(request));
    }

    @PostMapping("/create/user")
    @Operation(summary = "create user")
    @ApiResponse(responseCode = "200", description = "Create user",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserRequest.class)))
    @ApiResponse(responseCode = "400", description = "Username or email already exists")
    public ResponseEntity <StandardResponse> createUser(@RequestBody CreateUserRequest request) throws UserException{        
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/create/admin")
    @Operation(summary = "create admin")
    @ApiResponse(responseCode = "200", description = "Create admin",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserRequest.class)))
    @ApiResponse(responseCode = "400", description = "Username or email already exists")
    public ResponseEntity <StandardResponse> createAdmin(@RequestBody CreateUserRequest request) throws UserException{        
        return ResponseEntity.ok(userService.createAdmin(request));
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
