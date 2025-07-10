package webthuetro.auth_service.controller;


import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import webthuetro.auth_service.dto.request.ChangePassRequest;
import webthuetro.auth_service.dto.request.EditAccountRequest;
import webthuetro.auth_service.dto.request.LoginRequest;
import webthuetro.auth_service.dto.request.RegisterRequest;
import webthuetro.auth_service.dto.response.AccountResponse;
import webthuetro.auth_service.dto.response.ApiResponse;
import webthuetro.auth_service.dto.response.LoginResponse;
import webthuetro.auth_service.service.AccountService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) throws JOSEException {
        return accountService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<List<String>>> register(@RequestBody @Valid RegisterRequest registerRequest) {
       return accountService.register(registerRequest);
    }

    @GetMapping("/account")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(HttpServletRequest request) {
        return accountService.getAccount(request);
    }

    @PutMapping("/changePass")
    public ResponseEntity<ApiResponse<String>> changePass(HttpServletRequest request, @RequestBody ChangePassRequest changePassRequest) {
        return accountService.changePass(request, changePassRequest);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<AccountResponse>> edit(@RequestBody @Valid EditAccountRequest editAccountRequest, HttpServletRequest request) {
        return accountService.editAccount(request, editAccountRequest);
    }
}
