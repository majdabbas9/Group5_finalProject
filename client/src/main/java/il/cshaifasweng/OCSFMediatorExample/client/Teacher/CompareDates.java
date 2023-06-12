package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CompareDates {
    public static Boolean occurBeforeNow(String date1) throws ParseException {
        String now = LocalDateTime.now().toString();
        String year=now.substring(0,4);
        String month=now.substring(5,7);
        String day=now.substring(8,10);
        String hourMinute=now.substring(11,16);
        String date2="";
        date2+=year+"-"+month+"-"+day+" "+hourMinute;

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = sdFormat.parse(date1);
        Date d2 = sdFormat.parse(date2);
        if(d1.compareTo(d2)<0)return false; // if d1 occur before d2 return false
        return true;
    }
    public static String getCurrentTimeHour()
    {
        return LocalDateTime.now().toString().substring(11,13);
    }
    public static String getCurrentTimeMinute()
    {
        return LocalDateTime.now().toString().substring(14,16);
    }
}
