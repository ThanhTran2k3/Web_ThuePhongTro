package webthuetro.auth_service.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import webthuetro.auth_service.dto.request.UserClientRequest;
import webthuetro.auth_service.dto.response.UserClientResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserClient {
    private final WebClient webClient;

    public UserClient(WebClient.Builder builder) {
        String userApi = "http://localhost:8082/api/user";
        this.webClient = builder.baseUrl(userApi).build();
    }

    public UserClientResponse createUser(UserClientRequest userClientRequest) {
        Map<String, String> body = new HashMap<>();
        body.put("userName", userClientRequest.getUserName());
        if(!userClientRequest.getAvatar().isEmpty()){
            body.put("avatar", userClientRequest.getAvatar());
        }
        return webClient.post()
                .uri("/create")
                .bodyValue(body)
                .exchangeToMono(response ->
                        response.bodyToMono(UserClientResponse.class)
                )
                .block();
    }

}
