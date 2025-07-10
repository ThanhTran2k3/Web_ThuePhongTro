package webthuetro.auth_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import webthuetro.auth_service.entity.Account;
import webthuetro.auth_service.exception.AppException;
import webthuetro.auth_service.exception.ErrorCode;
import webthuetro.auth_service.repository.AccountRepository;
import webthuetro.auth_service.service.AccountService;

import java.util.Optional;

@AllArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, String> {

    private final AccountService accountService;
    private final AccountRepository accountRepository;


    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null || input.trim().isEmpty()) {
            return true;
        }
        Optional<Account> existAccount = accountService.findExistingAccount(input);

        if (existAccount.isEmpty()) {
            return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            String accountId = jwtAuth.getToken().getSubject();

            return existAccount
                    .map(account -> account.getAccountId().equals(accountId))
                    .orElse(false);
        }

        return false;

    }



}
