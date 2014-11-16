package edu.iastate.data;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlemons on 11/3/14.
 */
public class IntervalGenerator {
    public static List<Interval> getIntervals(List<String> days, Time startTime, Time stopTime)
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YY-MM-DDH:mm:ss");

        List<Interval> tmpMeetTimeIntervals = new ArrayList<Interval>();
        LocalDate mondayDate = LocalDate.parse("2014-10-20");
        for (int x = 0; x<= 5; x++)
        {
            LocalDate tmpStartDate = mondayDate.plusDays(x);
            String tmpDay = tmpStartDate.toString("EEEE");
            if (days.contains(tmpDay))
            {
                DateTime tmpStartDateTime = DateTime.parse(tmpStartDate.toString("YY-MM-DD") + LocalTime.fromDateFields(startTime).toString("H:mm:ss"), fmt);
                DateTime tmpStopDateTime = DateTime.parse(tmpStartDate.toString("YY-MM-DD") + LocalTime.fromDateFields(stopTime).toString("H:mm:ss"), fmt);
                tmpMeetTimeIntervals.add(new Interval(tmpStartDateTime, tmpStopDateTime));
            }
        }

        return tmpMeetTimeIntervals;
    }
}
