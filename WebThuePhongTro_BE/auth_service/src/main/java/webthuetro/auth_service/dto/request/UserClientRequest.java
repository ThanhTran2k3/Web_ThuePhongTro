package webthuetro.auth_service.dto.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserClientRequest {
    private String userName;
    private String avatar;
}
