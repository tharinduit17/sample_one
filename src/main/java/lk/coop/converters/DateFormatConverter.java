package lk.coop.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatConverter {

    public  String patternDate(Date request) {
        return new SimpleDateFormat("yyyy-MM-dd").format(request);
    }

    public  String patternDateTime(Date request) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request);
    }

    public Date convert(String arg0) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return simpleDateFormat.parse(arg0);
        } catch (ParseException e) {
            return null;
        }
    }
}
