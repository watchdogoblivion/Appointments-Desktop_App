/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sj_sc
 */
public class CustomerController implements Initializable {

   @FXML private Label custdetails;
   @FXML private Label custInfo;
   @FXML private Button closeBtn;
   
   private void setLanguage(){
        
    ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.LOCALE);
    custdetails.setText(rb.getString("customerDetails"));
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
        custInfo.setText(Customer.details.getText());
    }    
    
}
