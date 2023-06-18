/*
 * Copyright (c) 2023.
 */

package Utils;

import org.jetbrains.annotations.NotNull;
import org.joda.time.*;
import org.joda.time.chrono.GJChronology;
import org.joda.time.chrono.IslamicChronology;

public class DateConvert {
    DateTimeZone zone = DateTimeZone.forID("Asia/Riyadh");
    Chronology GJChronologydate = GJChronology.getInstance(zone);
    Chronology hijri = IslamicChronology.getInstance(zone);

    public LocalDate ConvertGregorianToHijri(@NotNull String Date) {
        //Convert from Gregorian Date String To Hijri Date Object
        //  "31/12/2023" format Date
        String[] values = Date.split("/");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);
        // DateTimeZone zone = DateTimeZone.forID("Asia/Riyadh");
        // Chronology GJChronologydate = GJChronology.getInstance(zone);
        //Chronology hijri = IslamicChronology.getInstance(zone);
        LocalDate todayGJChronologydate = new LocalDate(year, month, day, GJChronologydate);
        LocalDate todayHijri = new LocalDate(todayGJChronologydate.toDateTimeAtStartOfDay(), hijri);
        System.out.println("Hijri Data :::: " + todayHijri.toString("yyyy/MM/dd") + "\n" + "todayGJChronologydate ::: " + todayGJChronologydate.toString("yyyy/MM/dd")); // 1434-05-19
        return todayHijri;

    }

    public String AddDayToHijriDate(@NotNull String Date) {
        //Add One Day to Hijri Date String and return Hijri Date String
        //  "10/08/1444" format Date
        String[] values = Date.split("/");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);
        //  DateTimeZone zone = DateTimeZone.forID("Asia/Riyadh");
        //  Chronology hijri = IslamicChronology.getInstance(zone);
        LocalDate todayHijri = new LocalDate(year, month, day, hijri);
        System.out.println("Hijri Data :::: " + todayHijri.plusDays(1).toString("dd/MM/yyyy") + "\n"); // 1434-05-19
        return todayHijri.plusDays(1).toString("dd/MM/yyyy");
    }

    public LocalDate HijriDate(@NotNull String Date) {
        //Convert from Hijri Date string to Hijri Date Object
        String[] values = Date.split("/");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);

        //   DateTimeZone zone = DateTimeZone.forID("Asia/Riyadh");
        //  Chronology GJChronologydate = GJChronology.getInstance(zone);
        //   Chronology hijri = IslamicChronology.getInstance(zone);
        //   ConvertedGJChronologydate = new LocalDate(year, month, day, GJChronologydate);
    //    LocalDate ConvertedHijridate = new LocalDate(ConvertedGJChronologydate.toDateTimeAtStartOfDay(),hijri);
        LocalDate ConvertedHijridate = new LocalDate(year, month, day, hijri);
        return  ConvertedHijridate;
    }
    public LocalDateTime GregorianDateTime(@NotNull String Date,String [] Separator) {
        //Convert from Gregorian Date string to Hijri Date Object
        String[] WholeString = Date.split(Separator[0]);
        String[] Date1 = WholeString[0].split(Separator[1]);
        int day = Integer.parseInt(Date1[2]);
        int month = Integer.parseInt(Date1[1]);
        int year = Integer.parseInt(Date1[0]);
        //  DateTimeZone zone = DateTimeZone.forID("Asia/Riyadh");
        // Chronology GJChronologyDate = GJChronology.getInstance(zone);
        String[] Time1 = WholeString[1].split(":");
        int hours = Integer.parseInt(Time1[0]);
        int Minutes = Integer.parseInt(Time1[1]);
        LocalDate ConvertedDate = new LocalDate(year, month, day, GJChronologydate);
        LocalTime ConvertedTime = new LocalTime(hours, Minutes, 0, 0, GJChronologydate);
        return ConvertedDate.toLocalDateTime(ConvertedTime);
    }
}
