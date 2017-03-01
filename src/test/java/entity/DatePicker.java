package entity;

import org.joda.time.DateTime;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static utils.Constants.*;

public class DatePicker {

    private String month;
    private String day;
    private String year;
    private String date;
    private Calendar calendar;

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public DatePicker setDate(Date date) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            setMonth();
            System.out.println(calendar.getTime());
            DateTime dt = new DateTime(date);
            year = String.valueOf(dt.getYear());
            if(dt.getDayOfMonth()<=9) {
                day = "0"+String.valueOf(dt.getDayOfMonth());
            }else
            day = String.valueOf(dt.getDayOfMonth());
            this.date = day+" "+getMonth()+" "+year;
        return this;
    }

    private void setMonth() {
        SimpleDateFormat monthFormat = new SimpleDateFormat(MONTHFORMAT);
        month = monthFormat.format(calendar.getTime());
    }

}
