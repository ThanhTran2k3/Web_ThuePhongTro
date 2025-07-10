package webthuetro.api_gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenApiRoute {
    private String path;
    private String method;
}