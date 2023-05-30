package msv.management.system.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static HashMap<String, Long> getCurrentTimeRange() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date monthLastDay = calendar.getTime();


        long startDateStr = monthFirstDay.getTime();
        long endDateStr = monthLastDay.getTime();

        HashMap<String, Long> map = new HashMap();
        map.put("start_time", TimeUnit.MILLISECONDS.toSeconds(startDateStr));
        map.put("end_time", TimeUnit.MILLISECONDS.toSeconds(endDateStr));
        return map;
    }


}
