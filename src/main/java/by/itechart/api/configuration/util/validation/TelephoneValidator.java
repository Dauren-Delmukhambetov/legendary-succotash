package by.itechart.api.configuration.util.validation;

import by.itechart.api.configuration.util.annotation.Telephone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("(^$|[0-9]{10})");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
