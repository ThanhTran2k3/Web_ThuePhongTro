package webthuetro.user_service.service;

import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webthuetro.user_service.client.FileClient;
import webthuetro.user_service.dto.request.UserEditRequest;
import webthuetro.user_service.dto.request.UserCreateRequest;
import webthuetro.user_service.dto.response.ApiResponse;
import webthuetro.user_service.dto.response.FileClientResponse;
import webthuetro.user_service.entity.User;
import webthuetro.user_service.exception.AppException;
import webthuetro.user_service.exception.ErrorCode;
import webthuetro.user_service.mapper.UserMapper;
import webthuetro.user_service.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileClient fileClient;


    public String getUserIdFromToken(HttpServletRequest request) {
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

    public ResponseEntity<ApiResponse<User>> getMe(HttpServletRequest request) {
        String userId = getUserIdFromToken(request);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        return ResponseEntity.ok(
                ApiResponse.<User>builder()
                        .success(true)
                        .time(LocalDateTime.now())
                        .result(user)
                        .build()
        );
    }


    public ResponseEntity<ApiResponse<String>> create(UserCreateRequest request) {

        User user = userMapper.toUser(request);
        userRepository.save(user);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .time(LocalDateTime.now())
                        .result(user.getUserId())
                        .build()
        );
    }


    public ResponseEntity<ApiResponse<User>> edit(UserEditRequest userEditRequest, HttpServletRequest request) throws IOException {

        String userId = getUserIdFromToken(request);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        if(userEditRequest.getUserName() != null)
            user.setUserName(userEditRequest.getUserName());
        if(userEditRequest.getBirthDay() != null)
            user.setBirthDay(userEditRequest.getBirthDay());
        if(userEditRequest.getGender() != null)
            user.setGender(userEditRequest.getGender());
        if(!userEditRequest.getAvatar().isEmpty()){
            FileClientResponse fileClientResponse = fileClient.upload(userEditRequest.getAvatar());
            if(fileClientResponse.isSuccess()){
                if(!user.getAvatar().contains("default.png") && user.getAvatar().contains("cloudinary.com")){
                    FileClientResponse fileClientResponse1 = fileClient.delete(user.getAvatar());
                    if(!fileClientResponse1.isSuccess()){
                        throw new AppException(ErrorCode.ERROR_SYSTEM);
                    }
                }
                user.setAvatar(fileClientResponse.getResult());

            }
            else
                throw new AppException(ErrorCode.ERROR_SYSTEM);
        }
        return ResponseEntity.ok(
                ApiResponse.<User>builder()
                        .success(true)
                        .time(LocalDateTime.now())
                        .result(user)
                        .build()
        );
    }

    public Optional<User> findExistingUser(String input) {
        return userRepository.findByUserName(input);
    }
}
