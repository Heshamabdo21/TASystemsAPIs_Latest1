/*
 * Copyright (c) 2023.
 */

package PostgresqlUtils;

import Utils.DateConvert;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.chrono.GJChronology;

import java.sql.*;
public class SteeringCompanyQry {


   static DateConvert MyDate;
    static DateTimeZone zone;
    static Chronology GJChronologydate;
    public static void UpdateLastCreationalPeriod(LocalDate FromDate,LocalDate ToDate){
        MyDate=new DateConvert();

        zone = org.joda.time.DateTimeZone.forID("Asia/Riyadh");
        GJChronologydate = GJChronology.getInstance(zone);
        LocalTime EndDay=new LocalTime(23,0,0,0,GJChronologydate);

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager
                    .getConnection("jdbc:postgresql://172.24.78.90:5432/demo1",
                            "naqaba", "naqaba");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            Statement stmt = connection.createStatement();
            ResultSet SelectLastCreationalPeriod = stmt.executeQuery( "SELECT * FROM tic.inventory_operational_setting_specifications  " +
                    " order by ioss_id desc " +
                    "limit 1;" );
            int ioss_id = 0,ioss_iosp_id,ioss_creation_start_date_hij,ioss_creation_end_date_hij,ioss_state;
            Timestamp ioss_creation_start_date,ioss_creation_end_date;
            while ( SelectLastCreationalPeriod.next() ) {
                 ioss_id = SelectLastCreationalPeriod.getInt("ioss_id");
                 ioss_iosp_id = SelectLastCreationalPeriod.getInt("ioss_iosp_id");
                 ioss_creation_start_date  = SelectLastCreationalPeriod.getTimestamp("ioss_creation_start_date");
                 ioss_creation_start_date_hij = SelectLastCreationalPeriod.getInt("ioss_creation_start_date_hij");
                 ioss_creation_end_date  = SelectLastCreationalPeriod.getTimestamp("ioss_creation_end_date");
                 ioss_creation_end_date_hij = SelectLastCreationalPeriod.getInt("ioss_creation_end_date_hij");
                 ioss_state = SelectLastCreationalPeriod.getInt("ioss_state");

                //  float salary = rs.getFloat("salary");
                System.out.println( "ioss_id = " + ioss_id );
                System.out.println( "ioss_iosp_id = " + ioss_iosp_id );
                System.out.println( "ioss_creation_start_date = " + ioss_creation_start_date );
                System.out.println( "ioss_creation_start_date_hij = " + ioss_creation_start_date_hij );
                System.out.println( "ioss_creation_end_date = " + ioss_creation_end_date );
                System.out.println( "ioss_creation_end_date_hij = " + ioss_creation_end_date_hij );
                System.out.println( "ioss_state = " + ioss_state );

                System.out.println();
            }
            SelectLastCreationalPeriod.close();
            stmt.close();
            System.out.println("Selecting Operation done successfully");
            System.out.println("MyDate :::: "+MyDate.ConvertGregorianToHijri(FromDate.toString("dd/MM/YYYY")).toString("YYYYMMdd"));
            System.out.println("MyDate :::: "+MyDate.ConvertGregorianToHijri(ToDate.toString("dd/MM/YYYY")).toString("YYYYMMdd"));

            String sql = "UPDATE tic.inventory_operational_setting_specifications\n" +
                    "SET ioss_creation_start_date='"+ FromDate.toDateTimeAtStartOfDay().toString("YYYY-MM-dd HH:MM:ss")+"', " +
                   // "SET ioss_creation_start_date='2023-02-15 07:00:00.000', " +

                    //"ioss_creation_start_date_hij=14440724, " +
                    "ioss_creation_start_date_hij="+MyDate.ConvertGregorianToHijri(FromDate.toString("dd/MM/YYYY")).toString("YYYYMMdd")+", " +

                    //     "ioss_creation_end_date='2023-02-16 07:00:00.000', " +
                    "ioss_creation_end_date='"+ ToDate.toDateTime(EndDay).toString("YYYY-MM-dd HH:MM:ss")+"', " +

                   // "ioss_creation_end_date_hij=14440725, " +
                    "ioss_creation_end_date_hij="+MyDate.ConvertGregorianToHijri(ToDate.toString("dd/MM/YYYY")).toString("YYYYMMdd")+", " +

                    "ioss_state=1, " +
                    "change_comments='ByCodeTest', " +
                    "change_db_username='naqaba'" +
                    "WHERE ioss_id="+ioss_id+";";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
            System.out.println("Updating Operation done successfully");
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

    }

//    public static void main( String[] args ) {
//        DateTimeZone zone = org.joda.time.DateTimeZone.forID("Asia/Riyadh");
//        Chronology GJChronologydate = GJChronology.getInstance(zone);
//        LocalDate Today = new LocalDate(GJChronologydate);
//        UpdateLastCreationalPeriod(Today,Today.plusDays(1));
//    }
}
