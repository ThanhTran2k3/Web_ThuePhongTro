package webthuetro.user_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webthuetro.user_service.dto.request.UserEditRequest;
import webthuetro.user_service.dto.request.UserCreateRequest;
import webthuetro.user_service.dto.response.ApiResponse;
import webthuetro.user_service.entity.User;
import webthuetro.user_service.service.UserService;

import java.io.IOException;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> create(@RequestBody @Valid UserCreateRequest request) {
        return userService.create(request);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> me(HttpServletRequest request){
        return userService.getMe(request);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<User>> edit(@ModelAttribute @Valid UserEditRequest userEditRequest, HttpServletRequest request) throws IOException {
        return userService.edit(userEditRequest, request);
    }
}
