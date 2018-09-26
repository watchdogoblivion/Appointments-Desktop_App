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
import samuel_dorilas.security.Database_Credentials;

/**
 *
 * @author sj_sc
 */
public class Login extends Application {


	 private static final String driver = Database_Credentials.getDriver();
     private static final String db = Database_Credentials.getDb();
     private static final String url = Database_Credentials.getUrl();
     private static final String user = Database_Credentials.getUser();
     private static final String pass = Database_Credentials.getPass();
    
     private static String userNameStored;
     private static int userIDStored;
     private static Locale LOCALE = new Locale("en");
    
    public static String getDriver() {
		return driver;
	}

	public static String getDb() {
		return db;
	}

	public static String getUrl() {
		return url;
	}

	public static String getUser() {
		return user;
	}

	public static String getPass() {
		return pass;
	}

	public static Locale getLOCALE() {
		return LOCALE;
	}

	public static void setLOCALE(Locale lOCALE) {
		LOCALE = lOCALE;
	}

	public static String getUserNameStored() {
		return userNameStored;
	}

	public static void setUserNameStored(String userNameStored) {
		Login.userNameStored = userNameStored;
	}

	public static int getUserIDStored() {
		return userIDStored;
	}

	public static void setUserIDStored(int userIDStored) {
		Login.userIDStored = userIDStored;
	}
	
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
