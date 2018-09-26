/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import samuel_dorilas.Login;
import samuel_dorilas.models.Appointment;

/**
 * FXML Controller class
 *
 * @author sj_sc
 */


public class UpdateAptController implements Initializable {


    @FXML private Label updateAppointment;
    @FXML private Label custNameLabel;
    @FXML private Label staffLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label contactLabel;
    @FXML private Label typeLabel;
    @FXML private Label startDateTimeLabel;
    @FXML private Label endTimeLabel;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;
    
    @FXML private TextField custNameF;
    @FXML private TextField staffName;
    @FXML private TextField descriptionF;
    @FXML private MenuButton menuLocation;
    @FXML private TextField contactF;
    @FXML private MenuButton menuType;   
    @FXML private MenuButton menuStartTime;
    @FXML private DatePicker startDate;
    @FXML private MenuButton menuEndTime;
    private String introMeet = "";
    private String followUp = "";
    private String secondFollowUp = "";
    
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private LocalTime startTimeData;
    private LocalDate startDateData;
    private LocalTime endTimeData;
    private int AID;
    private int userID;
    private final Instant now = Instant.now();
    private final Timestamp aptLastUpdate =  Timestamp.from(now);
    private final String aptLastUpdateBy = Login.getUserNameStored();
    private String alert7 = "";
    private String alert8 = "";
    
    private DateTimeFormatter formatter2;
    
    private void setLanguage(){
        
    ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
    
    alert7 = rb.getString("alert7");
    alert8 = rb.getString("alert8");
    
    updateAppointment.setText(rb.getString("updateApt"));
    custNameLabel.setText(rb.getString("customerName"));
    staffLabel.setText(rb.getString("staff"));
    descriptionLabel.setText(rb.getString("description"));
    locationLabel.setText(rb.getString("location"));
    contactLabel.setText(rb.getString("contact"));
    typeLabel.setText(rb.getString("type"));
    startDateTimeLabel.setText(rb.getString("startDateAndTime"));
    endTimeLabel.setText(rb.getString("endTime"));
    
    introMeet = rb.getString("introMeet");
    followUp = rb.getString("followUp");
    secondFollowUp = rb.getString("secondFollowUp");
    
    menuLocation.setText(rb.getString("selectLocation"));
    menuType.setText(rb.getString("selectAptType"));
    menuStartTime.setText(rb.getString("selectTime"));
    startDate.setPromptText(rb.getString("selectDate"));
    menuEndTime.setText(rb.getString("selectTime"));
    saveBtn.setText(rb.getString("save"));
    cancelBtn.setText(rb.getString("cancel"));
    
    formatter2 = DateTimeFormatter.ofPattern("hh:mm a", Login.getLOCALE());
    }
    
    @FXML private void cancel(MouseEvent m){
        ((Stage)(cancelBtn.getScene().getWindow())).close();
    }
    
    //checkTimes is not needed since code set up accounts for its logic, can remove if code is re-used
    private void checkTimes(){
            if(startDateTime.toLocalDateTime().toLocalTime().isAfter(LocalTime.of(16, 59)) |
                    startDateTime.toLocalDateTime().toLocalTime().isBefore(LocalTime.of(9, 0))){
                throw new IndexOutOfBoundsException();
            }else if(endDateTime.toLocalDateTime().toLocalTime().isAfter(LocalTime.of(16, 59)) |
                    endDateTime.toLocalDateTime().toLocalTime().isBefore(LocalTime.of(9, 0))){
                throw new IndexOutOfBoundsException();
            }
    }
    
    @FXML private void save(ActionEvent e){
        try{
            
            if(menuStartTime.getText().substring(6).equals("PM")){
                startDateTime = Timestamp.valueOf(startDate.getValue().atTime(LocalTime.parse(
                    menuStartTime.getText().substring(0, 5)).plusHours(12)));   
            }else{
                startDateTime = Timestamp.valueOf(startDate.getValue().atTime(LocalTime.parse(
                    menuStartTime.getText().substring(0, 5))));
            }
            startTimeData =  startDateTime.toLocalDateTime().toLocalTime();
            startDateData = startDateTime.toLocalDateTime().toLocalDate();
                
            if(menuEndTime.getText().substring(6).equals("PM")){
                endDateTime = Timestamp.valueOf(startDate.getValue().atTime(LocalTime.parse(
                    menuEndTime.getText().substring(0, 5)).plusHours(12)));   
            }else{
                endDateTime = Timestamp.valueOf(startDate.getValue().atTime(LocalTime.parse(
                    menuEndTime.getText().substring(0, 5))));
            }
            endTimeData =  endDateTime.toLocalDateTime().toLocalTime();    
                
            checkTimes();
            
            for(Appointment apt: Appointment.getAptList()){
                if(AID != apt.getAptID() & apt.getUserID() == userID & apt.getStartDate().equals(startDateData) & 
                        startTimeData.isAfter(apt.getStartTime().minusMinutes(1)) & startTimeData.isBefore(apt.getEndTime().plusMinutes(1))){
                    throw new Overlap("The starting time overlaps with other appointments");
                }
                if(AID != apt.getAptID() & apt.getUserID() == userID & apt.getStartDate().equals(startDateData) & 
                        endTimeData.isAfter(apt.getStartTime().minusMinutes(1)) & endTimeData.isBefore(apt.getEndTime().plusMinutes(1))){
                    throw new Overlap("The ending time overlaps with other appointments");
                }
                
            }
            
            try(Connection conn = DriverManager.getConnection(Login.getUrl(),Login.getUser(),Login.getPass());){
                Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
             
                
            
                stmt.executeUpdate("UPDATE appointment SET title =" + "'" + menuType.getText() + "', description ='" + descriptionF.getText() +
                    "', location ='" + menuLocation.getText() + "', contact ='" + contactF.getText() + "', start ='" + startDateTime +
                    "', end ='" + endDateTime + "', lastUpdate ='" + aptLastUpdate + "', lastUpdateBy ='" +
                    aptLastUpdateBy + "' WHERE appointmentId =" + Appointment.getAptList().get(Appointment.getAptIndex()).getAptID());
              
            }catch(SQLException s){ s.printStackTrace(); ((Stage)(cancelBtn.getScene().getWindow())).close();}
            Appointment.getAptList().get(Appointment.getAptIndex()).setDisplayed(descriptionF.getText(), menuLocation.getText(), 
                contactF.getText(), menuType.getText(), startTimeData, startDateData, endTimeData,
                aptLastUpdate, aptLastUpdateBy);
            Appointment.getAptList().add(0, new Appointment()); Appointment.getAptList().remove(0);  
       
            ((Stage)(cancelBtn.getScene().getWindow())).close();
         
        }catch(IndexOutOfBoundsException n){
            Alert alert = new Alert(Alert.AlertType.WARNING, 
                alert7, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }catch(Overlap o){
            o.getMessage();
                
            Alert alert = new Alert(Alert.AlertType.WARNING, 
                alert8, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }    
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage();
        startDate.setOnShowing(show -> Locale.setDefault(Locale.Category.FORMAT,Login.getLOCALE()));
        
        for (int m = 0; m < 3; m++){
          MenuItem mI = new MenuItem();
          mI.setOnAction(e -> menuLocation.setText(mI.getText()));
          menuLocation.getItems().add(mI);
        }
        menuLocation.getItems().get(0).setText("Phoenix, Arizona");
        menuLocation.getItems().get(1).setText("London England");
        menuLocation.getItems().get(2).setText("New York, New York");
        
        LocalTime startHours = LocalTime.of(9, 0);
        for (int m = 0; m < 16; m++){
          MenuItem mI = new MenuItem();
          mI.setText(startHours.format(formatter2));
          mI.setOnAction(e -> menuStartTime.setText(mI.getText()));
          menuStartTime.getItems().add(mI);
          startHours = startHours.plusMinutes(30);
        }
        
        LocalTime endHours = LocalTime.of(9, 0);
        for (int m = 0; m < 16; m++){
          MenuItem mI = new MenuItem();
          mI.setText(endHours.format(formatter2));
          mI.setOnAction(e -> menuEndTime.setText(mI.getText()));
          menuEndTime.getItems().add(mI);
          endHours = endHours.plusMinutes(30);
        }
        
        for (int m = 0; m < 4; m++){
          MenuItem mI = new MenuItem();
          mI.setOnAction(e -> menuType.setText(mI.getText()));
          menuType.getItems().add(mI);
        }
        menuType.getItems().get(0).setText(introMeet);
        menuType.getItems().get(1).setText(followUp);
        menuType.getItems().get(2).setText(secondFollowUp);
        menuType.getItems().get(3).setText("N/A");
        
        AID = Appointment.getAptList().get(Appointment.getAptIndex()).getAptID();
        userID = Appointment.getAptList().get(Appointment.getAptIndex()).getUserID();
        custNameF.setText(Appointment.getAptList().get(Appointment.getAptIndex()).getCustName());
        staffName.setText(Appointment.getAptList().get(Appointment.getAptIndex()).getAptCreatedBy());
        descriptionF.setText(Appointment.getAptList().get(Appointment.getAptIndex()).getDescription());
        menuLocation.setText(Appointment.getAptList().get(Appointment.getAptIndex()).getLocation());
        contactF.setText(Appointment.getAptList().get(Appointment.getAptIndex()).getContact());
        menuType.setText(Appointment.getAptList().get(Appointment.getAptIndex()).getType());
        menuStartTime.setText(Appointment.getAptList().get(Appointment.getAptIndex()).getStartTime().format(formatter2));
        startDate.setValue(Appointment.getAptList().get(Appointment.getAptIndex()).getStartDate());
        menuEndTime.setText(Appointment.getAptList().get(Appointment.getAptIndex()).getEndTime().format(formatter2));
        
        
    }    
    
}

/*
class Overlaps extends InvalidObjectException{
    Overlaps(String s){super(s);}
}
*/