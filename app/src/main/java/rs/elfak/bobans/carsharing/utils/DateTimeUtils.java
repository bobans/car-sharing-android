package rs.elfak.bobans.carsharing.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class DateTimeUtils {

    public static String printLongDate(DateTime date) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMMM yyyy");
        return dtf.print(date);
    }

}
