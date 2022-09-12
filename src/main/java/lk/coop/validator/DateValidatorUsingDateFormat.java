package lk.coop.validator;

import lk.coop.validator.impl.DateValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorUsingDateFormat implements DateValidator {
    private DateTimeFormatter dateFormatter;

    public DateValidatorUsingDateFormat(DateTimeFormatter dateFormat) {
        this.dateFormatter = dateFormat;
    }

    @Override
    public boolean isValid(String dateStr) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormat);
//        simpleDateFormat.setLenient(false);
        try {
           LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
