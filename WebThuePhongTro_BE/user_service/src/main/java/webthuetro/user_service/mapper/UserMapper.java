package webthuetro.user_service.mapper;

import org.mapstruct.Mapper;
import webthuetro.user_service.dto.request.UserCreateRequest;
import webthuetro.user_service.entity.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateRequest userRequest);

}
