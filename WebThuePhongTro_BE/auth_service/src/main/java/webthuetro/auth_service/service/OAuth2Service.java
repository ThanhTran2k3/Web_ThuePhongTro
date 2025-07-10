package webthuetro.auth_service.service;


import com.nimbusds.jose.JOSEException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import webthuetro.auth_service.client.UserClient;
import webthuetro.auth_service.dto.request.UserClientRequest;
import webthuetro.auth_service.dto.response.UserClientResponse;
import webthuetro.auth_service.entity.Account;
import webthuetro.auth_service.exception.AppException;
import webthuetro.auth_service.exception.ErrorCode;
import webthuetro.auth_service.repository.AccountRepository;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OAuth2Service {
    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String clientSecret;

    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final WebClient webClient;
    private final UserClient userClient;

    public OAuth2Service(AccountRepository accountRepository,
                         JwtService jwtService,
                         WebClient.Builder webClientBuilder,
                         UserClient userClient) {
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
        this.webClient = webClientBuilder.build();
        this.userClient = userClient;
    }

    public ResponseEntity<Void> googleCallback(String code) throws JOSEException {
        String accessToken = exchangeCodeForAccessToken(code);
        Map<String, Object> userInfo = getUserInfo(accessToken);

        String googleId = (String) userInfo.get("sub");

        Optional<Account> optionalAccount = findExistingAccountWithGooGle(googleId);
        Account account = optionalAccount.orElseGet(() -> registerWithGoogle(userInfo));
        String token = jwtService.generateToken(account);
        URI redirectUri = URI.create("http://localhost:5173/oauth2/success?token=" + token);
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }

    private String exchangeCodeForAccessToken(String code) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("code", code);
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("redirect_uri", "http://localhost:8080/api/oauth2/google");
        form.add("grant_type", "authorization_code");

        Map<String, Object> response = webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(form))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        assert response != null;
        return (String) response.get("access_token");
    }

    private Map<String, Object> getUserInfo(String accessToken) {
        return webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    public Optional<Account> findExistingAccountWithGooGle(String googleId) {
        return accountRepository.findByGoogleId(googleId);
    }

    public Account registerWithGoogle(Map<String, Object> userInfo) {

        UserClientRequest userClientRequest = UserClientRequest.builder()
                .userName((String) userInfo.get("name"))
                .avatar((String) userInfo.get("picture"))
                .build();
        UserClientResponse userClientResponse = userClient.createUser(userClientRequest);
        if(userClientResponse.isSuccess()){
            Account account = Account.builder()
                    .accountId(userClientResponse.getResult())
                    .email((String) userInfo.get("email"))
                    .googleId((String) userInfo.get("sub"))
                    .googleName((String) userInfo.get("name"))
                    .emailVerified(true)
                    .linkedGoogle(true)
                    .build();

            return accountRepository.save(account);
        }
        throw new AppException(ErrorCode.ERROR_SYSTEM);
    }
}
