package webthuetro.api_gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import webthuetro.api_gateway.service.JwtService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilterConfig> {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter(JwtService jwtService) {
        super(AuthenticationFilterConfig.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(AuthenticationFilterConfig config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            String method = exchange.getRequest().getMethod().name();

            boolean isPublic = config.getOpenApiRoutes() != null &&
                    config.getOpenApiRoutes().stream()
                            .anyMatch(route -> route.getPath().equals(path)
                                    && route.getMethod().equalsIgnoreCase(method));

            if (isPublic) {
                return chain.filter(exchange);
            }

            String authHeader = Objects.requireNonNullElse(
                    exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION),
                    ""
            );

            if (!authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Bạn không có quyền truy cập", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                Claims claims = jwtService.validateToken(token);
                String userId = claims.getSubject();

                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(builder -> builder.header("X-User-Id", userId))
                        .build();

                return chain.filter(mutatedExchange);
            } catch (Exception e) {
                return onError(exchange, "Bạn không có quyền truy cập", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", false);
        responseBody.put("time", LocalDateTime.now().toString());
        responseBody.put("error", message);

        try {
            String json = objectMapper.writeValueAsString(responseBody);
            DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            return response.setComplete();
        }
    }

}
