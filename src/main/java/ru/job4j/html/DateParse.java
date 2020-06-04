package ru.job4j.html;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class Date. This class provides methods for working with dates.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 03.06.2020
 */

public class DateParse {
    private final static String[] MONTHS = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};

    private static String convertMonth(String month) {
        int result = 0;
        for (int i = 0; i < MONTHS.length; i++) {
            if (MONTHS[i].equals(month)) {
                result = i + 1;
                break;
            }
        }
        return Integer.toString(result);
    }

    /**
     * Gets date.
     *
     * @param date the date
     * @return the date
     */
    public static Timestamp getDate(String date) {
        String[] arr = date.split(" ");
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        if (arr[0].equals("сегодня,")) {
            sb.append(sdf.format(calendar.getTime())).append(" ")
                    .append(arr[1]).append(":00");

        } else if (arr[0].equals("вчера,")) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            sb.append(sdf.format(calendar.getTime())).append(" ")
                    .append(arr[1]).append(":00");

        } else {
            sb.append("20").append(arr[2], 0, 2).append("-")
                    .append(convertMonth(arr[1])).append("-")
                    .append(arr[0]).append(" ")
                    .append(arr[3]).append(":00");
        }
        return Timestamp.valueOf(sb.toString());
    }
}
