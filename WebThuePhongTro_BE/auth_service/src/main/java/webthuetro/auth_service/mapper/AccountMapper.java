package webthuetro.auth_service.mapper;

import org.mapstruct.Mapper;
import webthuetro.auth_service.dto.request.LoginRequest;
import webthuetro.auth_service.dto.request.RegisterRequest;
import webthuetro.auth_service.dto.response.AccountResponse;
import webthuetro.auth_service.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(LoginRequest loginRequest);

    Account toAccount(RegisterRequest registerRequest);

    AccountResponse toAccountResponse(Account account);
}
