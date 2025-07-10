package webthuetro.auth_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserClientResponse {

    private boolean success;
    private LocalDateTime time;
    private String result;
    private List<String> error;

}
