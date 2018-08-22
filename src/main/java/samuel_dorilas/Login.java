/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas;

import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sj_sc
 */
public class Login extends Application {
    /*
     static final String driver = "com.mysql.jdbc.Driver";
     static final String db = "desktopscheduler";
     static final String url = "jdbc:mysql://127.0.0.1/" + db;
     static final String user = "root";
     static final String pass = "password";
    */
    /*
     static final String driver = "com.mysql.jdbc.Driver";
     static final String db = "U04mIO";
     static final String url = "jdbc:mysql://52.206.157.109/" + db;
     static final String user = "U04mIO";
     static final String pass = "53688284838";
    */

     static final String driver = Database_Credentials.driver;
     static final String db = Database_Credentials.db;
     static final String url = Database_Credentials.url;
     static final String user = Database_Credentials.user;
     static final String pass = Database_Credentials.pass;
    
     static String userNameStored;
     static int userIDStored;
     static Locale LOCALE = new Locale("en");
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       

    }
    
}
