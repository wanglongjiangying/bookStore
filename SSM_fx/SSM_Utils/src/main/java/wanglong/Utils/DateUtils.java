package wanglong.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class DateUtils {

    public static String dateToString(Date date,String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    public static Date stringToDate(String time,String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date=null;
        try {
             date= dateFormat.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }


    }
}
