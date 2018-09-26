/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import samuel_dorilas.Login;
import samuel_dorilas.models.Appointment;

/**
 * FXML Controller class
 *
 * @author sj_sc
 */
public class AppointmentController implements Initializable {

    @FXML private Label aptDetails;
    @FXML private Label aptInfo;
    @FXML Button closeBtn;
    
    private void setLanguage(){
        
    ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
    aptDetails.setText(rb.getString("aptDetails"));
    closeBtn.setText(rb.getString("close"));
    }
    
    @FXML private void close(MouseEvent m){
        ((Stage)(closeBtn.getScene().getWindow())).close();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage();
        aptInfo.setText(Appointment.getAptDetails().getText());
        
    }    
    
}
