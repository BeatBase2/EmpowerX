/*
Author: Abdulmuhaimin Ali
Date: 2023-01-20
This code uses Calender class to represent deadline. It uses the Map class to store the deadlines by task name.  */
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ScheduleCalendar {
    private Map<String, Calendar> deadlines;

    public ScheduleCalendar() {
        deadlines = new HashMap<String, Calendar>();
    }

    public void addDeadline(String task, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        deadlines.put(task, calendar);
    }

    public Calendar getDeadline(String task) {
        return deadlines.get(task);
    }

}
