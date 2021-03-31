package by.itechart.api.configuration.util.validation;

import by.itechart.api.configuration.util.annotation.Telephone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^[+{375}]*\\s[(][0-9]{2}[)]\\s[0-9]{3}[-][0-9]{2}[-][0-9]{2}");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
