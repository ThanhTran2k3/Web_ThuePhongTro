package webthuetro.user_service.dto.request;

import lombok.Builder;
import lombok.Data;
import webthuetro.user_service.validator.Unique;

@Data
@Builder
public class UserCreateRequest {

    @Unique(message = "Tên người dùng đã tồn tại")
    private String userName;

    private String avatar;
}
