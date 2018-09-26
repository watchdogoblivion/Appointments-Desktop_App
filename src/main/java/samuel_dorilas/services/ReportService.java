package samuel_dorilas.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;

import javafx.scene.control.TextArea;
import samuel_dorilas.Login;
import samuel_dorilas.models.Appointment;

public class ReportService {
	
	public void consultantScheduleReport(String aptSchedule, String report2, TextArea reports) {
        //Code for indefinte number of users
        ArrayList<String> schedule = new ArrayList<>();
        String reportsText = "";
        try(Connection conn = DriverManager.getConnection(Login.getUrl(),Login.getUser(),Login.getPass());){
        
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs = stmt.executeQuery("SELECT userName, userId FROM user");
            while(rs.next()){
                int usersID = rs.getInt("userId");
                String userName = "\n" + rs.getString("userName") + "'s " + aptSchedule + ":";
                schedule.add(userName);
                reportsText = reportsText.concat(userName);
                for(Appointment apt: Appointment.getAptList()){
                    if(apt.getUserID() == usersID){
                        String mySchedule = "\n\t" +apt.toString3() + "\n";
                        schedule.add(mySchedule);
                        reportsText = reportsText.concat(mySchedule);
                    }
                }   
            }
        
        }catch(SQLException e){e.printStackTrace();}
        try {
            Path path = Paths.get(report2);
            Files.write(path, schedule, StandardCharsets.UTF_8);
        }catch (IOException e) {e.printStackTrace();}
        reports.setText(reportsText);
	}
	
	public void appointmentTypeReport(String introMeet,String followUp, String secondFollowUp,
			String report1, TextArea reports) {
        
        ArrayList<String> months = new ArrayList<>();
        String reportsText = "";
        Month month = Month.JANUARY;
        for (int j = 0; j < 12; j++) {
            String monthLabel = "\n" + month.getDisplayName(TextStyle.FULL, Login.getLOCALE()) + ":";
            String type1 = "\n\t" + introMeet + ":";
            int one = 0;
            String type2 = "\n\t" + followUp + ":";
            int two = 0;
            String type3 = "\n\t"+ secondFollowUp + ":";
            int three = 0;
            String type4 = "\n\tN/A:";
            int four = 0;
            for (Appointment apt: Appointment.getAptList()){
                if(apt.getStartDate().getMonth().equals(month)){
                    ArrayList <Appointment> appoint = new ArrayList<>();
                    appoint.add(apt);
                    for (Appointment ap: appoint){
                        if(ap.getType().equalsIgnoreCase(introMeet)){
                            one++;
                        }else if(ap.getType().equalsIgnoreCase(followUp)){
                            two++;
                        }else if(ap.getType().equalsIgnoreCase(secondFollowUp)){
                            three++;
                        }else {
                            four++;
                        }
                    }
                }
            }
            type1 = type1.concat(" " + one);
            type2 = type2.concat(" " + two);
            type3 = type3.concat(" " + three);
            type4 = type4.concat(" " + four);
            //months.add(monthLabel.concat(type1+type2+type3+type4)); Does not lead to proper display
            months.add(monthLabel); 
            months.add(type1); months.add(type2);
            months.add(type3); months.add(type4);
            reportsText = reportsText.concat(monthLabel+type1+type2+type3+type4);
            month = month.plus(1);
            
        }
        try {
            Path path = Paths.get(report1);
            Files.write(path, months, StandardCharsets.UTF_8);
        }catch (IOException e) {e.printStackTrace();}
        reports.setText(reportsText);
        //reports.setText(months.toString()); Does not Lead t proper Display
	}
	
	public void upcomingAppointmentsReport(String upcomingApts, String noUpcomingApts, 
			String report3, TextArea reports) {
        //Code for indefinte number of users
        ArrayList<String> upcoming = new ArrayList<>(50);
        String reportsText = "";
        try(Connection conn = DriverManager.getConnection(Login.getUrl(),Login.getUser(),Login.getPass());){
        
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs = stmt.executeQuery("SELECT userName, userId FROM user");
            while(rs.next()){
                int usersID = rs.getInt("userId");
                String userName = "\n" + rs.getString("userName") + "'s "+ upcomingApts + ":";
                upcoming.add(userName);
                reportsText = reportsText.concat(userName);
                int count = 0;
                for(Appointment apt: Appointment.getAptList()){
                    if(apt.getUserID() == usersID & apt.getStartDate().equals(LocalDate.now())){
                        count++;
                        String mySchedule = "\n\t" +apt.toString3() + "\n";
                        upcoming.add(mySchedule);
                        reportsText = reportsText.concat(mySchedule);
                    }
                }
                if(count == 0){
                    String none ="\n\t" + noUpcomingApts +"\n";
                    upcoming.add(none);
                    reportsText = reportsText.concat(none);
                } 
            }
        
        }catch(SQLException e){e.printStackTrace();}
        try {
            Path path = Paths.get(report3);
            Files.write(path, upcoming, StandardCharsets.UTF_8);
        }catch (IOException e) {e.printStackTrace();}
        reports.setText(reportsText);
	}
}
