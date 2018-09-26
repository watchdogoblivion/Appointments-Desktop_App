package samuel_dorilas.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import samuel_dorilas.Login;

public class UserDAOService {
	public void saveUserName(TextField profileName, MenuButton userOptions, String alert10) {
        try(Connection conn = DriverManager.getConnection( Login.getUrl(),Login.getUser(),Login.getPass());){
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
           
            stmt.executeUpdate("UPDATE user SET userName ='" + profileName.getText()
            + "' WHERE userId =" + Login.getUserIDStored());
            
            Login.setUserNameStored(profileName.getText());
            userOptions.setText(Login.getUserNameStored());
            
            Alert alert = new Alert(Alert.AlertType.NONE, 
                alert10, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {alert.close();}

        }catch(SQLException e){e.printStackTrace();}
	}
	
	public void savePassword(TextField newPass, TextField verifyPass, String alert11, String alert12) {
        String password1 = newPass.getText();
        String password2 = verifyPass.getText();
        newPass.clear();
        verifyPass.clear();
        
        if(password1.equals(password2)){
            try(Connection conn = DriverManager.getConnection( Login.getUrl(),Login.getUser(),Login.getPass());){
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
           
            stmt.executeUpdate("UPDATE user SET password ='" + password1
            + "' WHERE userId =" + Login.getUserIDStored());
            
            Alert alert = new Alert(Alert.AlertType.NONE, 
                alert11, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {alert.close();}

            }catch(SQLException e){
                e.printStackTrace();
            }
        }else{
           Alert alert = new Alert(Alert.AlertType.NONE, 
                alert12, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {alert.close();} 
        }
	}
}
