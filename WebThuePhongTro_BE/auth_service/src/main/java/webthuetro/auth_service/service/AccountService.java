package webthuetro.auth_service.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webthuetro.auth_service.client.UserClient;
import webthuetro.auth_service.dto.request.*;
import webthuetro.auth_service.dto.response.AccountResponse;
import webthuetro.auth_service.dto.response.ApiResponse;
import webthuetro.auth_service.dto.response.LoginResponse;
import webthuetro.auth_service.dto.response.UserClientResponse;
import webthuetro.auth_service.entity.Account;
import webthuetro.auth_service.exception.AppException;
import webthuetro.auth_service.exception.ErrorCode;
import webthuetro.auth_service.mapper.AccountMapper;
import webthuetro.auth_service.repository.AccountRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AccountMapper accountMapper;
    private final UserClient userClient;

    public ResponseEntity<ApiResponse<List<String>>> register(RegisterRequest registerRequest) {
        UserClientRequest userClientRequest = UserClientRequest.builder()
                .userName(registerRequest.getUserName())
                .build();
        UserClientResponse userClientResponse = userClient.createUser(userClientRequest);

        if(userClientResponse.isSuccess()){
            String pass = registerRequest.getPassword();
            registerRequest.setPassword(new BCryptPasswordEncoder().encode(pass));
            Account account = accountMapper.toAccount(registerRequest);
            account.setAccountId(userClientResponse.getResult());

            accountRepository.save(account);

            return ResponseEntity.ok(
                    ApiResponse.<List<String>>builder()
                            .success(true)
                            .time(LocalDateTime.now())
                            .message("Đăng ký thành công")
                            .build()
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.<List<String>>builder()
                        .success(false)
                        .time(LocalDateTime.now())
                        .error(userClientResponse.getError())
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<LoginResponse>> login (LoginRequest loginRequest) throws JOSEException {

        Account account = findExistingAccount(loginRequest.getAccount())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        boolean isSuccess = validateAccount(account,loginRequest.getPassword());
        if(!isSuccess)
            throw new AppException(ErrorCode.ERROR_USER_LOGIN);

        String accessToken = jwtService.generateToken(account);
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(accessToken)
                .build();
        return ResponseEntity.ok(
               ApiResponse.<LoginResponse>builder()
                       .success(true)
                       .time(LocalDateTime.now())
                       .result(loginResponse)
                       .build()
        );
    }

    public Optional<Account> findExistingAccount(String input) {
        if (input.matches("^0\\d+$")) {
            return accountRepository.findByPhoneNumber(input);
        }

        return accountRepository.findByEmail(input);
    }

    public boolean validateAccount(Account account, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, account.getPassword())) {
            return false;
        }
        if (account.isDelete()) {
            throw new AppException(ErrorCode.BLOCK_ACCOUNT);
        }
        return true;
    }

    public String getAccountIdFromToken(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            String token = authorizationHeader.substring(7);
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception ex) {
            throw new AppException(ErrorCode.USER_NOT_EXIST);
        }
    }

    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(HttpServletRequest request) {
        String userId = getAccountIdFromToken(request);
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        AccountResponse accountResponse = accountMapper.toAccountResponse(account);
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .time(LocalDateTime.now())
                        .result(accountResponse)
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<String>> changePass(HttpServletRequest request, ChangePassRequest changePassRequest){
        String accountId = getAccountIdFromToken(request);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        if (account.getPassword() == null || account.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .success(false)
                            .time(LocalDateTime.now())
                            .error("Tài khoản này chưa đặt mật khẩu")
                            .build()
            );
        }

        boolean isSuccess = validateAccount(account,changePassRequest.getCurrentPass());
        if(!isSuccess){
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .success(false)
                            .time(LocalDateTime.now())
                            .error("Mật khẩu hiện tại không đúng")
                            .build()
            );
        }
        String pass = changePassRequest.getNewPass();
        account.setPassword(new BCryptPasswordEncoder().encode(pass));
        accountRepository.save(account);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .time(LocalDateTime.now())
                        .result("Đổi mật khẩu thành thông")
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<AccountResponse>> editAccount(HttpServletRequest request, EditAccountRequest editAccountRequest){
        String accountId = getAccountIdFromToken(request);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        if(editAccountRequest.getEmail() != null)
            account.setEmail(editAccountRequest.getEmail());
        if(editAccountRequest.getPhoneNumber() != null)
            account.setPhoneNumber(editAccountRequest.getPhoneNumber());
        AccountResponse accountResponse = accountMapper.toAccountResponse(account);
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .success(true)
                        .time(LocalDateTime.now())
                        .result(accountResponse)
                        .build()
        );
    }
}
