/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import samuel_dorilas.Login;

/**
 *
 * @author sj_sc
 */
public class LoginFXMLController implements Initializable {
//    final double width = 1325;
//    final double height = 700;
    private String alert1;
    private String alert2;
    private String alert3;
    private String alert4;
    @FXML private Hyperlink forgot;
    @FXML private TextField userNameField;
    @FXML private PasswordField passwordField;
    @FXML private Button login;
    
    @FXML
    private void login(ActionEvent event) {
        
        if(validateCredentials()){
        try{  
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               
//            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//            mainStage.setX(((screenBounds.getWidth() - width) / 2)-10); 
//            mainStage.setY(((screenBounds.getHeight() - height) / 2)-15);         
        
            Scene mainScene = new Scene(mainRoot);
                
            mainScene.getStylesheets().add("/styles/Main.css");
                    
            mainStage.setScene(mainScene);
            mainStage.setTitle("Main Screen"); 
            mainStage.setMaximized(true);
            mainStage.show();
                
        }catch(IOException e){e.printStackTrace();}
        }
        
    }
    
    private boolean validateCredentials(){
        try(Connection conn = DriverManager.getConnection( Login.getUrl(),Login.getUser(),Login.getPass());){
            Statement stmt1 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE); //for username
            Statement stmt2 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE); //for password
            Statement stmt3 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE); //to store userid
            Login.setUserNameStored(userNameField.getText());
            String passStored = passwordField.getText();
            passwordField.clear();
            
            ResultSet rs1 =  stmt1.executeQuery("SELECT userName FROM user WHERE userName="+ "'" + Login.getUserNameStored() + "'");
            ResultSet rs2 =  stmt2.executeQuery(
                        "SELECT userName, password FROM user WHERE userName="+ 
                                "'" + Login.getUserNameStored()+ "'" + " AND password="+ "'" + passStored + "'");
            ResultSet rs3 =  stmt3.executeQuery(
                        "SELECT userId FROM user WHERE userName="+ 
                                "'" + Login.getUserNameStored()+ "'" + " AND password="+ "'" + passStored + "'");
            
            if(Login.getUserNameStored().equals("")){
                 Alert alert = new Alert(Alert.AlertType.NONE, 
                                 alert1, ButtonType.OK);
                                 alert.showAndWait();
                                 if (alert.getResult() == ButtonType.OK) {alert.close();}
                return false;
            }else if(passStored.equals("")){
                Alert alert = new Alert(Alert.AlertType.NONE, 
                                 alert2, ButtonType.OK);
                                 alert.showAndWait();
                                 if (alert.getResult() == ButtonType.OK) {alert.close();}
                return false;
            }else if(rs1.next()){
                if(rs2.next()){
                    rs3.next();
                    Login.setUserIDStored(rs3.getInt("userId"));
                    return true;
                }else{
                    Alert alert = new Alert(Alert.AlertType.NONE, 
                                 alert3, ButtonType.OK);
                                 alert.showAndWait();
                                 if (alert.getResult() == ButtonType.OK) {alert.close();}
                    return false;
                } 
            }else {
                Alert alert = new Alert(Alert.AlertType.NONE, 
                                 alert4, ButtonType.OK);
                                 alert.showAndWait();
                                 if (alert.getResult() == ButtonType.OK) {alert.close();}
                return false;
            }
           
        }catch(SQLException e){
            e.printStackTrace(); 
            return false;
        }
      
    }
    
    private void setLang(){
    ResourceBundle rb = ResourceBundle.getBundle("properties/Login", Login.getLOCALE());
    userNameField.setPromptText(rb.getString("userName"));
    passwordField.setPromptText(rb.getString("password"));
    login.setText(rb.getString("login"));
    alert1 = rb.getString("alert1");
    alert2 = rb.getString("alert2");
    alert3 = rb.getString("alert3");
    alert4 = rb.getString("alert4");
    forgot.setText(rb.getString("forgot"));
    }
   

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLang();  
    }    
    
}
