package webthuetro.user_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import webthuetro.user_service.entity.User;
import webthuetro.user_service.service.UserService;

import java.util.Optional;

@AllArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, String> {

    private final UserService userService;


    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {

        Optional<User> existUser = userService.findExistingUser(input);

        return existUser.isEmpty();
    }



}
