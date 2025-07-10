package webthuetro.auth_service.dto.request;

import lombok.Data;

@Data
public class ChangePassRequest {

    private String currentPass;
    private String newPass;
}
