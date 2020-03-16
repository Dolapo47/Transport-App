package transport.app.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import transport.app.demo.model.user.User;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        if(user.getPassword().length() < 6){
            errors.rejectValue("password", "Length", "Password must be at least 6 characters");
            if(!user.getPassword().equals(user.getConfirmPassword())){
                errors.rejectValue("confirmPassword", "Match", "Password must match");
            }
        }
    }
}
