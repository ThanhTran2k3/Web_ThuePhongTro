package webthuetro.auth_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    private String phoneNumber;
    private String email;
    private boolean linkedGoogle;
    private String googleName;
    private boolean emailVerified;
    private boolean phoneVerified;

}
