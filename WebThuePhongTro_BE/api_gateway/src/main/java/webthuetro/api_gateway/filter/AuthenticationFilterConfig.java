package webthuetro.api_gateway.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import webthuetro.api_gateway.model.OpenApiRoute;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "gateway")
public class AuthenticationFilterConfig {

    private List<OpenApiRoute> openApiRoutes = List.of(
        new OpenApiRoute("/api/auth/login", "POST"),
        new OpenApiRoute("/api/auth/register", "POST"),
        new OpenApiRoute("/api/oauth2/google", "GET")
    );
}