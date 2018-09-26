/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.controllers;

import java.io.InvalidObjectException;
import java.net.URL;
import java.sql.Timestamp;
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
import samuel_dorilas.models.Customer;
import samuel_dorilas.services.AppointmentDAOService;

/**
 * FXML Controller class
 *
 * @author sj_sc
 */
public class CreateAppointmentController implements Initializable {

    @FXML private Label createAppointment;
    @FXML private Label aptIDLabel;
    @FXML private Label custIDLabel;
    @FXML private Label staffLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label contactLabel;
    @FXML private Label typeLabel;
    @FXML private Label startDateTimeLabel;
    @FXML private Label endTimeLabel;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;
    @FXML private TextField AIDF;
    @FXML private TextField custIDF;
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
    private LocalDate startDateData;
    private LocalTime startTimeData;
    private LocalTime endTimeData;
    private final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("hh:mm a", Login.getLOCALE());
    private final int userID = Login.getUserIDStored();
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private final String aptCreatedBy = Login.getUserNameStored();
    private String alert7 = "";
    private String alert8 = "";
    private String alert9 = "";
    
    AppointmentDAOService aDAOS = new AppointmentDAOService();
    
    private void setLanguage(){
        
	    ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
	    
	    alert7 = rb.getString("alert7");
	    alert8 = rb.getString("alert8");
	    alert9 = rb.getString("alert9");
	    
	    createAppointment.setText(rb.getString("createApt"));
	    aptIDLabel.setText(rb.getString("appointmentID"));
	    custIDLabel.setText(rb.getString("customerID"));
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
	    
	           saveBtn.disableProperty().bind((custIDF.textProperty().isEqualTo(""))
	                .or(descriptionF.textProperty().isEqualTo(""))
	                .or(menuLocation.textProperty().isEqualTo(rb.getString("selectLocation")))
	                .or(contactF.textProperty().isEqualTo(""))
	                .or(menuType.textProperty().isEqualTo(rb.getString("selectAptType")))
	                .or(menuStartTime.textProperty().isEqualTo(rb.getString("selectTime")))
	                .or(startDate.editorProperty().getValue().textProperty().isEqualTo(""))//.getEditor().textProperty().isEqualTo("")) either works
	                .or(menuEndTime.textProperty().isEqualTo(rb.getString("selectTime"))));
    }
    
    @FXML private void cancel(MouseEvent m){
        ((Stage)(cancelBtn.getScene().getWindow())).close();
    }
    
    //checkTimes is not needed since code set up accounts for its logic, can remove if code is re-used
    private void checkTimes(){
            if(startTimeData.isAfter(LocalTime.of(16, 59)) || startTimeData.isBefore(LocalTime.of(9, 0)) ||
                    endTimeData.isAfter(LocalTime.of(16, 59)) || endTimeData.isAfter(LocalTime.of(16, 59))){
                throw new NullPointerException();
            }else if(endDateTime.toLocalDateTime().toLocalTime().isAfter(LocalTime.of(16, 59)) |
                    endDateTime.toLocalDateTime().toLocalTime().isBefore(LocalTime.of(9, 0))){
                throw new IndexOutOfBoundsException();
            }
    }
    @FXML private void save(ActionEvent e){
        Boolean valid = false;
        for(Customer c: Customer.getCustList()){
            if(c.getCustID() == Integer.parseInt(custIDF.getText())){
                valid = true;
            }
        }
        if(valid){
              
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
            
            try{
                checkTimes();
                for(Appointment apt: Appointment.getAptList()){
                    if(apt.getUserID() == userID & apt.getStartDate().equals(startDateData) & 
                            startTimeData.isAfter(apt.getStartTime().minusMinutes(1)) & startTimeData.isBefore(apt.getEndTime().plusMinutes(1))){
                        throw new Overlap("The starting time overlaps with other appointments");
                    }
                    if(apt.getUserID() == userID & apt.getStartDate().equals(startDateData) & 
                            endTimeData.isAfter(apt.getStartTime().minusMinutes(1)) & endTimeData.isBefore(apt.getEndTime().plusMinutes(1))){
                        throw new Overlap("The ending time overlaps with other appointments");
                    }
                }
                aDAOS.createAppointment(startDateData, startTimeData, endTimeData, startDateTime, endDateTime,
            			custIDF.getText(), menuType.getText(), descriptionF.getText(), menuLocation.getText(),
            			contactF.getText());

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
            
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, 
                alert9, ButtonType.OK);
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
       AIDF.setPromptText("Auto");
       staffName.setText(aptCreatedBy);       
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
       
       
    } 
    
}

class Overlap extends InvalidObjectException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Overlap(String s){super(s);}
}
