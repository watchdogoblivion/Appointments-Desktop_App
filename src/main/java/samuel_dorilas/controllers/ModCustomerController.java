package samuel_dorilas.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
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
import samuel_dorilas.models.Customer;

/**
 * FXML Controller class
 *
 * @author sj_sc
 */
public class ModCustomerController implements Initializable {

    @FXML private Label modCustomer;
    @FXML private Label custNameLabel;
    @FXML private Label custActivityLabel;
    @FXML private Label custAddressLabel; 
    @FXML private Label custPostalLabel;
    @FXML private Label custPhoneLabel;
    @FXML private Label custCityLabel;
    @FXML private Label custCountryLabel;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;
    
    @FXML private TextField custNameF;
    @FXML private MenuButton activity;
    @FXML private TextField custAddressF;
    @FXML private TextField custPostalF;
    @FXML private TextField custPhoneF;
    @FXML private TextField custCityF;
    @FXML private TextField custCountryF;
    private final Instant now = Instant.now();
    private final Timestamp lastUpdate =  Timestamp.from(now);
    private final String lastUpdateBy = Login.getUserNameStored();
    private int active = 0;
    private String activeString ="";
    private String inActiveString = "";
    
   private void setLanguage(){
        
    ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
    
    activeString = rb.getString("active");
    inActiveString = rb.getString("inactive");
    
    modCustomer.setText(rb.getString("modifyCustomer"));
    custActivityLabel.setText(rb.getString("activity"));
    activity.setText(rb.getString("selectActivity"));
    custNameLabel.setText(rb.getString("customerName"));
    custAddressLabel.setText(rb.getString("address"));
    custPostalLabel.setText(rb.getString("postalCode"));
    custPhoneLabel.setText(rb.getString("phone"));
    custCityLabel.setText(rb.getString("city"));
    custCountryLabel.setText(rb.getString("country"));
    saveBtn.setText(rb.getString("save"));
    cancelBtn.setText(rb.getString("cancel"));

    }
    
    @FXML private void cancel(MouseEvent m){
        ((Stage)(cancelBtn.getScene().getWindow())).close();
    }
    
    @FXML private void save(ActionEvent e){
        
         if(activity.getText().equalsIgnoreCase(activeString)){
                active = 1;
            }
         
         try(Connection conn = DriverManager.getConnection(Login.getUrl(),Login.getUser(),Login.getPass());){
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
            
            
            
            stmt.executeUpdate("UPDATE customer SET customerName =" + "'" + custNameF.getText() + 
                    "', active ='" + active + "', lastUpdate ='" + lastUpdate + "', lastUpdateBy ='" +
                    lastUpdateBy + "' WHERE customerId =" + Customer.getCustList().get(Customer.getCustIndex()).getCustID());
            
            stmt.executeUpdate("UPDATE address SET address =" + "'" + custAddressF.getText() + 
                    "', postalCode ='" + custPostalF.getText() +
                    "', phone ='" + custPhoneF.getText() + "', lastUpdate ='" + lastUpdate + "', lastUpdateBy ='" +
                    lastUpdateBy + "' WHERE addressId =" + Customer.getCustList().get(Customer.getCustIndex()).getAddressId());
            
            stmt.executeUpdate("UPDATE city SET city =" + "'" + custCityF.getText() + 
                    "', lastUpdate ='" + lastUpdate + "', lastUpdateBy ='" +
                    lastUpdateBy + "' WHERE cityId =" + Customer.getCustList().get(Customer.getCustIndex()).getCityId());
            
            stmt.executeUpdate("UPDATE country SET country =" + "'" + custCountryF.getText() + 
                    "', lastUpdate ='" + lastUpdate + "', lastUpdateBy ='" +
                    lastUpdateBy + "' WHERE countryId =" + Customer.getCustList().get(Customer.getCustIndex()).getCountryId());
            
         }catch(SQLException s){ s.printStackTrace();}
         Customer.getCustList().get(Customer.getCustIndex()).setDisplayed(custNameF.getText(), active, 
                 lastUpdate, lastUpdateBy, custAddressF.getText(), custPostalF.getText(), custPhoneF.getText(), 
                 custCityF.getText(), custCountryF.getText());
         Customer.getCustList().add(0, new Customer()); Customer.getCustList().remove(0);  
         // I couldnt find another way to trigger the listener i set on the list since it only 
         //responds to adds and removes and not setters. It important in the refreshing of the table
         
         ((Stage)(cancelBtn.getScene().getWindow())).close();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage();
        for (int m = 0; m < 2; m++){
          MenuItem mI = new MenuItem();
          mI.setOnAction(e -> activity.setText(mI.getText()));
          activity.getItems().add(mI);
        }
        activity.getItems().get(0).setText(activeString);
        activity.getItems().get(1).setText(inActiveString);
        
        custNameF.setText(Customer.getCustList().get(Customer.getCustIndex()).getCustName());
        int activeInitialize = Customer.getCustList().get(Customer.getCustIndex()).getActive();
        if(activeInitialize == 1){
            activity.setText(activeString);
        }else{activity.setText(inActiveString);}
        custAddressF.setText(Customer.getCustList().get(Customer.getCustIndex()).getCustAddress());
        custPostalF.setText(Customer.getCustList().get(Customer.getCustIndex()).getCustPostal());
        custPhoneF.setText(Customer.getCustList().get(Customer.getCustIndex()).getCustPhone());
        custCityF.setText(Customer.getCustList().get(Customer.getCustIndex()).getCustCity());
        custCountryF.setText(Customer.getCustList().get(Customer.getCustIndex()).getCustCountry());
        
        
    }    
    
}
