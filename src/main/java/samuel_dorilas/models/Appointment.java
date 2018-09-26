/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import samuel_dorilas.Login;

/**
 *
 * @author sj_sc
 */
public class Appointment {
    
	private static int aptIndex;
	private static Label aptDetails = new Label();
	private static ObservableList<Appointment> aptList =
                  FXCollections.observableArrayList();
	private static ObservableList<Appointment> inList =
                  FXCollections.observableArrayList();
	private static ObservableList<Appointment> cancelList =
                  FXCollections.observableArrayList();
	private static ObservableList<Appointment> absentList =
                  FXCollections.observableArrayList();
    private DateTimeFormatter formatter;
    private DateTimeFormatter formatter2;
    private DateTimeFormatter formatter3;
    private DateTimeFormatter formatter4;
    private String info = "";
    private int aptID = 0;
    private int custID = 0;
    private int userID;
    private String description = "";
    private String location = "";
    private String contact = "";
    private String type = "";
    private LocalTime startTime;
    private LocalDate startDate;
    private LocalTime endTime;
    private Timestamp aptCreateDate;
    private String aptCreatedBy = "";
    private Timestamp aptLastUpdate;
    private String aptLastUpdateBy = "";
    private String custName = "";
    
    private String appointmentIDText;
    private String userIdText;
    private String customerIDText;
    private String customerNameText;
    private String descriptionText;
    private String locationText;
    private String contactText ;
    private String typeText ;
    private String startTimeText;
    private String startDateText;
    private String endTimeText;
    private String createDateText;
    private String createdByText;
    private String lastUpdateDateText;
    private String lastUpdateByText;
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private String text6;
    
    
    
    
    @Override
    public String toString(){
        ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
        appointmentIDText = rb.getString("appointmentID");
        userIdText = rb.getString("userId");
        customerIDText = rb.getString("customerID");
        customerNameText = rb.getString("customerName");
        descriptionText = rb.getString("description");
        locationText = rb.getString("location");
        contactText = rb.getString("contact");
        typeText = rb.getString("type");
        startTimeText= rb.getString("startTime");
        startDateText= rb.getString("startDate");
        endTimeText= rb.getString("endTime");
        createDateText = rb.getString("createDateText");
        createdByText= rb.getString("createdByText");
        lastUpdateDateText= rb.getString("lastUpdateDateText");
        lastUpdateByText= rb.getString("lastUpdateByText");
        
        formatter = DateTimeFormatter.ofPattern("hh:mm a");
        formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        formatter3 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a");
        formatter4 = DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy");
        
        return  appointmentIDText + ": " + aptID + "\n" + userIdText + ": "+ userID + "\n" + customerIDText +": " + custID  + 
                "\n" + customerNameText + ": "+ custName + "\n" + descriptionText+ ": " + description + "\n" + locationText + ": " + 
                location + "\n" + contactText + ": " + contact + "\n" + typeText + ": " + type + "\n" + startTimeText + ": " + startTime.format(formatter) + 
                "\n" + endTimeText + ": " + endTime.format(formatter) + "\n" + startDateText + ": " + startDate.format(formatter2) + 
                "\n" + createDateText + ": "+ aptCreateDate.toLocalDateTime().format(formatter3) + "\n" + createdByText + ": " + aptCreatedBy + 
                "\n" + lastUpdateDateText + ": " + aptLastUpdate.toLocalDateTime().format(formatter3) + "\n" + lastUpdateByText + ": " + aptLastUpdateBy; 
                
    }

    public String toString2(){
        ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
        text1 = rb.getString("text1");
        text2 = rb.getString("text2");
        text4 = rb.getString("text4");
        text5 = rb.getString("text5");
        text6 = rb.getString("text6");
        
        formatter = DateTimeFormatter.ofPattern("hh:mm a");
        formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        formatter3 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a");
        formatter4 = DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy");
        
        return  aptCreatedBy + " " + text1 + " " + startTime.format(formatter) + 
                " " + text2 + " " + endTime.format(formatter) + " " + text4 + " " + location + " " + text5 + " " +
                type + " " + text6 + " " + custName;
    }
    
    public String toString3(){
        ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
        text1 = rb.getString("text1");
        text2 = rb.getString("text2");
        text3 = rb.getString("text3");
        text4 = rb.getString("text4");
        text5 = rb.getString("text5");
        text6 = rb.getString("text6");
        
        formatter = DateTimeFormatter.ofPattern("hh:mm a");
        formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        formatter3 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a");
        formatter4 = DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy");
        
        return  aptCreatedBy + " " + text1 + " " + startTime.format(formatter) + 
                " "+ text2 +" " + endTime.format(formatter) + " " + text3 + " "+ startDate.format(formatter4) +
                " " + text4 + " " + location + " " + text5 + " " +
                type + " " + text6 + " " + custName;
    }
 
    public Appointment() {
        this(0, 0, 0,"","","","", LocalTime.now(), LocalDate.now(),LocalTime.now(),
                Timestamp.valueOf(LocalDateTime.now()), "", Timestamp.valueOf(LocalDateTime.now()), "", "");
    }
 
    public Appointment(int aptID, int custID, int userID, String description, 
            String location, String contact, String type, LocalTime startTime, LocalDate startDate, LocalTime endTime,
            Timestamp aptCreateDate, String aptCreatedBy, Timestamp aptLastUpdate, String aptLastUpdateBy, String custName) {
        setAptID(aptID);
        setCustID(custID);
        setUserID(userID);
        setDescription(description);
        setLocation(location);
        setContact(contact);
        setType(type);
        setStartTime(startTime);
        setStartDate(startDate);
        setEndTime(endTime);
        setAptCreateDate(aptCreateDate);
        setAptCreatedBy(aptCreatedBy);
        setAptLastUpdate(aptLastUpdate);
        setAptLastUpdateBy(aptLastUpdateBy);
        setCustName(custName);
        setInfo(toString2());
        
    }
    public Appointment(Appointment a) {
        setAptID(aptID);
        setCustID(custID);
        setUserID(userID);
        setDescription(description);
        setLocation(location);
        setContact(contact);
        setType(type);
        setStartTime(startTime);
        setStartDate(startDate);
        setEndTime(endTime);
        setAptCreateDate(aptCreateDate);
        setAptCreatedBy(aptCreatedBy);
        setAptLastUpdate(aptLastUpdate);
        setAptLastUpdateBy(aptLastUpdateBy);
        setCustName(custName);
        setInfo(toString2());
        
    }
    
    public String getInfo()
    {return info;}
    
    public void setInfo(String i)
    {info = i;}
    
    public int getAptID() 
    {return aptID;}
 
    public void  setAptID(int id) 
    {aptID = id;}
    
    public int getCustID() 
    {return custID;}
    
    public void  setUserID(int id) 
    {userID = id;}
    
    public int getUserID() 
    {return userID;}
 
    public void  setCustID(int id) 
    {custID = id;}
    
    public String getDescription() 
    {return description;}
    
    public void setDescription(String d) 
    {description = d;}
    
    public String getLocation() 
    {return location;}
    
    public void setLocation(String l) 
    {location = l;}
    
    public String getContact() 
    {return contact;}
    
    public void setContact(String p) 
    {contact = p;}
    
    public String getType() 
    {return type;}
    
    public void setType(String m) 
    {type = m;}
    
    public LocalTime getStartTime() 
    {return startTime;}
       
    public void setStartTime(LocalTime m) 
    {startTime = m;}
    
    public LocalDate getStartDate() 
    {return startDate;}
       
    public void setStartDate(LocalDate m) 
    {startDate = m;}
       
    public LocalTime getEndTime() 
    {return endTime;}
       
    public void setEndTime(LocalTime m) 
    {endTime = m;}
      
    public Timestamp getAptCreateDate() 
    {return aptCreateDate;}
    
    public void setAptCreateDate(Timestamp n) 
    {aptCreateDate = n;}
    
    public String getAptCreatedBy() 
    {return aptCreatedBy;}
    
    public void setAptCreatedBy(String a) 
    {aptCreatedBy = a;}

    public Timestamp getAptLastUpdate() 
    {return aptLastUpdate;}
    
    public void setAptLastUpdate(Timestamp n) 
    {aptLastUpdate = n;}
    
    public String getAptLastUpdateBy() 
    {return aptLastUpdateBy;}
    
    public void setAptLastUpdateBy(String a) 
    {aptLastUpdateBy = a;}
    
    public String getCustName() 
    {return custName;}
    
    public void setCustName(String n) 
    {custName = n;}

	public static int getAptIndex() {
		return aptIndex;
	}

	public static void setAptIndex(int aptIndex) {
		Appointment.aptIndex = aptIndex;
	}

	public static Label getAptDetails() {
		return aptDetails;
	}

	public static void setAptDetails(Label aptDetails) {
		Appointment.aptDetails = aptDetails;
	}

	public static ObservableList<Appointment> getAptList() {
		return aptList;
	}

	public static void setAptList(ObservableList<Appointment> aptList) {
		Appointment.aptList = aptList;
	}

	public static ObservableList<Appointment> getInList() {
		return inList;
	}

	public static void setInList(ObservableList<Appointment> inList) {
		Appointment.inList = inList;
	}

	public static ObservableList<Appointment> getCancelList() {
		return cancelList;
	}

	public static void setCancelList(ObservableList<Appointment> cancelList) {
		Appointment.cancelList = cancelList;
	}

	public static ObservableList<Appointment> getAbsentList() {
		return absentList;
	}

	public static void setAbsentList(ObservableList<Appointment> absentList) {
		Appointment.absentList = absentList;
	}
	
    
    public void setAll(int aptID, int custID, int userID, String description, 
            String location, String contact, String type, LocalTime startTime, LocalDate startDate, LocalTime endTime,
            Timestamp aptCreateDate, String aptCreatedBy, Timestamp aptLastUpdate, String aptLastUpdateBy, String custName) {
        setAptID(aptID);
        setCustID(custID);
        setUserID(userID);
        setDescription(description);
        setLocation(location);
        setContact(contact);
        setType(type);
        setStartTime(startTime);
        setStartDate(startDate);
        setEndTime(endTime);
        setAptCreateDate(aptCreateDate);
        setAptCreatedBy(aptCreatedBy);
        setAptLastUpdate(aptLastUpdate);
        setAptLastUpdateBy(aptLastUpdateBy);
        setCustName(custName);
        setInfo(toString2());
        
    }
    
    public void setDisplayed(String description, 
            String location, String contact, String type, LocalTime startTime, LocalDate startDate, LocalTime endTime,
             Timestamp aptLastUpdate, String aptLastUpdateBy) {
        
        setDescription(description);
        setLocation(location);
        setContact(contact);
        setType(type);
        setStartTime(startTime);
        setStartDate(startDate);
        setEndTime(endTime);
        setAptLastUpdate(aptLastUpdate);
        setAptLastUpdateBy(aptLastUpdateBy);
        setInfo(toString2());
        
    }
}
