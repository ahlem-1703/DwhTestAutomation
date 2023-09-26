package com.Springboot.example.helper;

import org.quartz.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Component
public class SchedulerHelper {
    private static final Map<String, Integer> dayOfWeekMap = new HashMap<>();
    static {
        dayOfWeekMap.put("SUNDAY", DayOfWeek.MONDAY.getValue());
        dayOfWeekMap.put("MONDAY", DayOfWeek.TUESDAY.getValue());
        dayOfWeekMap.put("TUESDAY", DayOfWeek.WEDNESDAY.getValue());
        dayOfWeekMap.put("WEDNESDAY", DayOfWeek.THURSDAY.getValue());
        dayOfWeekMap.put("THURSDAY", DayOfWeek.FRIDAY.getValue());
        dayOfWeekMap.put("FRIDAY", DayOfWeek.SATURDAY.getValue());
        dayOfWeekMap.put("SATURDAY", DayOfWeek.SUNDAY.getValue());
    }
    // Helper method to create a Daily trigger
    public Trigger createDailyTrigger(String scheduleName, LocalTime executionTime, String dailyDaysOfWeek,String endAtDaily) throws ParseException {        Set<Integer> daysOfWeek = parseDaysOfWeek(dailyDaysOfWeek);

        DailyTimeIntervalScheduleBuilder scheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .withIntervalInHours(24)
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(executionTime.getHour(), executionTime.getMinute()));

        if (!daysOfWeek.isEmpty()) {
            scheduleBuilder.onDaysOfTheWeek(daysOfWeek);
        }
        return TriggerBuilder.newTrigger()
                .withIdentity(scheduleName, "group1")
                .startNow()
                .withSchedule(scheduleBuilder)
                .endAt(new SimpleDateFormat("yyyy-MM-dd").parse(endAtDaily))
                .build();
    }

    // Helper method to create a Weekly trigger
    public static Trigger createWeeklyTrigger(String scheduleName, LocalTime weeklyExecutionTime, String WeeklyDaysOfWeek, String endTime) throws ParseException {
        Integer dayOfWeek = dayOfWeekMap.get(WeeklyDaysOfWeek);
        return TriggerBuilder.newTrigger()
                .withIdentity(scheduleName)
                .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(dayOfWeek, weeklyExecutionTime.getHour(), weeklyExecutionTime.getMinute())
                        .inTimeZone(TimeZone.getDefault()))
                .endAt(new SimpleDateFormat("yyyy-MM-dd").parse(endTime))
                .build();
    }
    // Helper method to parse days of the week from the user input
    public Set<Integer> parseDaysOfWeek(String dailyDaysOfWeek) {
        Set<Integer> daysOfWeekInt = new HashSet<>();
        if (dailyDaysOfWeek != null && !dailyDaysOfWeek.isEmpty()) {
            String[] dayStrings = dailyDaysOfWeek.split(",");
            for (String dayString : dayStrings) {
                Integer dayValue = dayOfWeekMap.get(dayString);
                if (dayValue != null) {
                    daysOfWeekInt.add(dayValue);
                } else {
                    // Handle invalid input or unknown DayOfWeek values if needed
                }
            }
        }
        return daysOfWeekInt;
    }

    // Helper method to create a Monthly trigger
    public Trigger createMonthlyTrigger(String scheduleName,LocalTime executionTime, int monthlyDateOfMonth,String endAt) throws ParseException {
        String cronExpression = String.format("0 %d %d %d * ?",
                executionTime.getMinute(), executionTime.getHour(), monthlyDateOfMonth);
        return TriggerBuilder.newTrigger()
                .withIdentity(scheduleName)
                .withSchedule(cronSchedule(cronExpression)
                        .withMisfireHandlingInstructionDoNothing()) // Évitez les déclenchements manqués
                .endAt(new SimpleDateFormat("dd/MM/yyyy").parse(endAt))
                .startNow()
                .build();
    }
}
