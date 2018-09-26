/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import samuel_dorilas.Login;
import samuel_dorilas.services.CustomerDAOService;


/**
 * FXML Controller class
 *
 * @author sj_sc
 */
public class AddCustomerController implements Initializable {
     
    @FXML private Label addCustomerLabel;
    @FXML private Label custIDLabel;
    @FXML private Label custNameLabel;
    @FXML private Label custActivityLabel;
    @FXML private Label custAddressLabel; 
    @FXML private Label custPostalLabel;
    @FXML private Label custPhoneLabel;
    @FXML private Label custCityLabel;
    @FXML private Label custCountryLabel;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;
    
    @FXML private TextField generateCID;
    @FXML private TextField custNameF;
    @FXML private MenuButton activity;
    @FXML private TextField custAddressF;
    @FXML private TextField custPostalF;
    @FXML private TextField custPhoneF;
    @FXML private TextField custCityF;
    @FXML private TextField custCountryF;
    private String activeString ="";
    private String inActiveString = "";
    private int active = 0;

    
    CustomerDAOService cDAOS = new CustomerDAOService();
    
    private void setLanguage(){
        
    ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
    
    activeString = rb.getString("active");
    inActiveString = rb.getString("inactive");
    addCustomerLabel.setText(rb.getString("addCustomer"));
    custActivityLabel.setText(rb.getString("activity"));
    activity.setText(rb.getString("selectActivity"));
    custIDLabel.setText(rb.getString("customerID"));
    custNameLabel.setText(rb.getString("customerName"));
    custAddressLabel.setText(rb.getString("address"));
    custPostalLabel.setText(rb.getString("postalCode"));
    custPhoneLabel.setText(rb.getString("phone"));
    custCityLabel.setText(rb.getString("city"));
    custCountryLabel.setText(rb.getString("country"));
    saveBtn.setText(rb.getString("save"));
    cancelBtn.setText(rb.getString("cancel"));
    
    
       saveBtn.disableProperty().bind((custNameF.textProperty().isEqualTo(""))
                .or(activity.textProperty().isEqualTo(rb.getString("selectActivity")))
                .or(custAddressF.textProperty().isEqualTo(""))
                .or(custPostalF.textProperty().isEqualTo(""))
                .or(custPhoneF.textProperty().isEqualTo(""))
                .or(custCityF.textProperty().isEqualTo(""))
                .or(custCountryF.textProperty().isEqualTo("")));

    }
    
    @FXML private void cancel(MouseEvent m){
        ((Stage)(cancelBtn.getScene().getWindow())).close();
    }
    
    @FXML private void save(ActionEvent e){
        if(activity.getText().equalsIgnoreCase(activeString)){
                active = 1;
            }
        
        cDAOS.addCustomer(active, custNameF.getText(), custAddressF.getText(), 
        		custPostalF.getText(), custPhoneF.getText(), custCityF.getText(), custCountryF.getText());   

        
        ((Stage)(cancelBtn.getScene().getWindow())).close();
    }
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       generateCID.setPromptText("Auto");
       setLanguage();
       
       for (int m = 0; m < 2; m++){
          MenuItem mI = new MenuItem();
          mI.setOnAction(e -> activity.setText(mI.getText()));
          activity.getItems().add(mI);
        }
        activity.getItems().get(0).setText(activeString);
        activity.getItems().get(1).setText(inActiveString);
        
        
    } 
    
}
