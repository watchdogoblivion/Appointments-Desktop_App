/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas;


import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author sj_sc
 */
public class MainController implements Initializable {
    
    private LocalDate currentDate = LocalDate.now();
    
    @FXML private Label title;
    @FXML private Label customers;
    @FXML private Label calendar;
    @FXML private Label appointment;
    @FXML private MenuButton userOptions;
    @FXML private MenuItem signOut;
    @FXML private MenuItem profile;
    @FXML private TabPane tabPane;
    private SingleSelectionModel  sM;
    
    @FXML private Tab tab1;
    @FXML private TextField searchField;
    @FXML private Label search;
    @FXML private TableView<Customer> custTable;
    @FXML private TableColumn<Customer, Integer> custIDCol;
    @FXML private TableColumn<Customer, String> custNameCol;
    @FXML private TableColumn<Customer, String> custAddressCol; 
    @FXML private TableColumn<Customer, String> custPostalCol;
    @FXML private TableColumn<Customer, String> custPhoneCol;
    @FXML private TableColumn<Customer, String> custCityCol;
    @FXML private TableColumn<Customer, String> custCountryCol;
    @FXML private Button addCust;
    @FXML private Button detailsCust;
    @FXML private Button deleteCust;
    @FXML private Button modCust;
    
    @FXML private Tab tab2;
    @FXML private Label calendarMonth;
    @FXML private Label next;
    @FXML private Label previous;
    @FXML private AnchorPane calendarPane;
    @FXML TableView<Appointment> calendarTable;
    private String calendarApts = "";
    @FXML private TableColumn<Appointment, String> calendarCol;
    private final ObservableList<Appointment> calendarList =
                  FXCollections.observableArrayList();
    private final MonthlyCalendarView MCV = new MonthlyCalendarView();
    private final WeeklyCalendarView WCV = new WeeklyCalendarView();
    @FXML private Label sunday;
    @FXML private Label monday;
    @FXML private Label tuesday;
    @FXML private Label wednesday;
    @FXML private Label thursday;
    @FXML private Label friday;
    @FXML private Label saturday;
    @FXML private Label viewByMonth;
    @FXML private Label viewByWeek;
    
    @FXML private Tab tab3;
    @FXML private Tab appointmentTab;
    @FXML private Tab upcomingTab;
    @FXML private Label search1;
    @FXML private TextField searchFieldApt;
    @FXML private TableView<Appointment> aptTable;
    @FXML private TableColumn<Appointment, String> staffCol;
    @FXML private TableColumn<Appointment, LocalTime> startTimeCol;
    @FXML private TableColumn<Appointment, LocalTime> endTimeCol;
    @FXML private TableColumn<Appointment, LocalDate> startDateCol;
    @FXML private TableColumn<Appointment, String> locationCol;
    @FXML private TableColumn<Appointment, String> contactCol;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private Button detailsApt;
    @FXML private Button createApt;
    @FXML private Button removeApt;
    @FXML private Button updateApt;
    @FXML private Button checkedInButton;
    @FXML private Button cancelledButton;
    @FXML private Button absentButton;
    
    @FXML private Tab inTab;
    @FXML private Label search2;
    @FXML private TextField searchFieldIn;
    @FXML private TableView<Appointment> inTable;
    @FXML private TableColumn<Appointment, String> staffCol2;
    @FXML private TableColumn<Appointment, LocalTime> startTimeCol2;
    @FXML private TableColumn<Appointment, LocalTime> endTimeCol2;
    @FXML private TableColumn<Appointment, LocalDate> startDateCol2;
    @FXML private TableColumn<Appointment, String> locationCol2;
    @FXML private TableColumn<Appointment, String> contactCol2;
    @FXML private TableColumn<Appointment, String> typeCol2;
    @FXML private Button detailsAptIn;
    
    @FXML private Tab cancelledTab;
    @FXML private Label search3;
    @FXML private TextField searchFieldCancel;
    @FXML private TableView<Appointment> cancelTable;
    @FXML private TableColumn<Appointment, String> staffCol3;
    @FXML private TableColumn<Appointment, LocalTime> startTimeCol3;
    @FXML private TableColumn<Appointment, LocalTime> endTimeCol3;
    @FXML private TableColumn<Appointment, LocalDate> startDateCol3;
    @FXML private TableColumn<Appointment, String> locationCol3;
    @FXML private TableColumn<Appointment, String> contactCol3;
    @FXML private TableColumn<Appointment, String> typeCol3;
    @FXML private Button detailsAptCancelled;
    
    @FXML private Tab absentTab;
    @FXML private Label search4;
    @FXML private TextField searchFieldAbsent;
    @FXML private TableView<Appointment> absentTable;
    @FXML private TableColumn<Appointment, String> staffCol4;
    @FXML private TableColumn<Appointment, LocalTime> startTimeCol4;
    @FXML private TableColumn<Appointment, LocalTime> endTimeCol4;
    @FXML private TableColumn<Appointment, LocalDate> startDateCol4;
    @FXML private TableColumn<Appointment, String> locationCol4;
    @FXML private TableColumn<Appointment, String> contactCol4;
    @FXML private TableColumn<Appointment, String> typeCol4;
    @FXML private Button detailsAptAbsent;
    
    @FXML private Tab reportsTab;
    @FXML private Label genrateType;
    @FXML private Button generateA;
    @FXML private Label generateSchedule;
    @FXML private Button generateB;
    @FXML private Label generateUpcoming;
    @FXML private Button generateC;
    @FXML private TextArea reports;
    private String introMeet = "";
    private String followUp = "";
    private String secondFollowUp = "";
    private String aptSchedule;
    private String upcomingApts;
    private String noUpcomingApts;
    private String report1;
    private String report2;
    private String report3;
    
    @FXML private Tab tab4;
    
    @FXML private Tab tab5;
    @FXML private Label profileID;
    @FXML private TextField profileIDF;
    @FXML private Label profileName;
    @FXML private TextField profileNameF;
    @FXML private CheckBox nameCheck;
    @FXML private Button nameSave;
    
    @FXML private Tab tab6;
    @FXML private Label newPass;
    @FXML private PasswordField newPassF;
    @FXML private Label verifyPass;
    @FXML private PasswordField verifyPassF;
    @FXML private CheckBox passCheck;
    @FXML private Button passSave;
    
    @FXML private Tab tab7;
    @FXML private Label selectLang;
    @FXML private RadioButton english;
    @FXML private RadioButton spanish;
    @FXML private RadioButton french;
    @FXML private Button saveLang;
    
    private String alert1;
    private String alert2;
    private String alert3;
    private String alert4;
    private String alert5;
    private String alert6;
    private String alert10;
    private String alert11;
    private String alert12;
    
    private DateTimeFormatter formatter;
    private DateTimeFormatter formatter2;
    private DateTimeFormatter formatter3;
    private DateTimeFormatter formatter4;
    
    private void setLanguage(){
        
    ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.LOCALE);
    
    alert1 = rb.getString("alert1");
    alert2 = rb.getString("alert2");
    alert3 = rb.getString("alert3");
    alert4 = rb.getString("alert4");
    alert5 = rb.getString("alert5");
    alert6 = rb.getString("alert6");
    alert10 = rb.getString("alert10");
    alert11 = rb.getString("alert11");
    alert12 = rb.getString("alert12");

    title.setText(rb.getString("schedulingApplication"));
    customers.setText(rb.getString("customers"));
    calendar.setText(rb.getString("calendar"));
    appointment.setText(rb.getString("appointment"));
    signOut.setText(rb.getString("signOut"));
    profile.setText(rb.getString("profile"));
    
    search.setText(rb.getString("search"));
    custIDCol.setText(rb.getString("customerID"));
    custNameCol.setText(rb.getString("customerName"));
    custAddressCol.setText(rb.getString("address"));
    custPostalCol.setText(rb.getString("postalCode"));
    custPhoneCol.setText(rb.getString("phone"));
    custCityCol.setText(rb.getString("city"));
    custCountryCol.setText(rb.getString("country"));
    addCust.setText(rb.getString("addCustomer"));
    detailsCust.setText(rb.getString("customerDetails"));
    deleteCust.setText(rb.getString("deleteCustomer"));
    modCust.setText(rb.getString("modifyCustomer"));
    
    calendarApts = rb.getString("appointmentTab");
    sunday.setText(rb.getString("sunday"));
    monday.setText(rb.getString("monday"));
    tuesday.setText(rb.getString("tuesday"));
    wednesday.setText(rb.getString("wednesday"));
    thursday.setText(rb.getString("thursday"));
    friday.setText(rb.getString("friday"));
    saturday.setText(rb.getString("saturday"));
    viewByMonth.setText(rb.getString("viewByMonth"));
    viewByWeek.setText(rb.getString("viewByWeek"));
    
    appointmentTab.setText(rb.getString("appointmentTab"));
    
    upcomingTab.setText(rb.getString("upcomingTab"));
    search1.setText(rb.getString("search"));
    staffCol.setText(rb.getString("staff"));
    startTimeCol.setText(rb.getString("startTime"));
    endTimeCol.setText(rb.getString("endTime"));
    startDateCol.setText(rb.getString("startDate"));
    locationCol.setText(rb.getString("location"));
    contactCol.setText(rb.getString("contact"));
    typeCol.setText(rb.getString("type"));
    detailsApt.setText(rb.getString("aptDetails"));
    createApt.setText(rb.getString("createApt"));
    removeApt.setText(rb.getString("removeApt"));
    updateApt.setText(rb.getString("updateApt"));
    checkedInButton.setText(rb.getString("checkedIn"));
    cancelledButton.setText(rb.getString("cancelled"));
    absentButton.setText(rb.getString("absent"));  
    
    inTab.setText(rb.getString("inTab"));
    search2.setText(rb.getString("search"));
    staffCol2.setText(rb.getString("staff"));
    startTimeCol2.setText(rb.getString("startTime"));
    endTimeCol2.setText(rb.getString("endTime"));
    startDateCol2.setText(rb.getString("startDate"));
    locationCol2.setText(rb.getString("location"));
    contactCol2.setText(rb.getString("contact"));
    typeCol2.setText(rb.getString("type"));
    detailsAptIn.setText(rb.getString("aptDetails"));
       
    cancelledTab.setText(rb.getString("cancelledTab"));
    search3.setText(rb.getString("search"));
    staffCol3.setText(rb.getString("staff"));
    startTimeCol3.setText(rb.getString("startTime"));
    endTimeCol3.setText(rb.getString("endTime"));
    startDateCol3.setText(rb.getString("startDate"));
    locationCol3.setText(rb.getString("location"));
    contactCol3.setText(rb.getString("contact"));
    typeCol3.setText(rb.getString("type"));
    detailsAptCancelled.setText(rb.getString("aptDetails"));
    
    absentTab.setText(rb.getString("absentTab"));
    search4.setText(rb.getString("search"));
    staffCol4.setText(rb.getString("staff"));
    startTimeCol4.setText(rb.getString("startTime"));
    endTimeCol4.setText(rb.getString("endTime"));
    startDateCol4.setText(rb.getString("startDate"));
    locationCol4.setText(rb.getString("location"));
    contactCol4.setText(rb.getString("contact"));
    typeCol4.setText(rb.getString("type"));
    detailsAptAbsent.setText(rb.getString("aptDetails"));
    
    reportsTab.setText(rb.getString("reportsTab"));
    genrateType.setText(rb.getString("generateType"));
    generateA.setText(rb.getString("generate"));
    generateSchedule.setText(rb.getString("generateSchedule"));
    generateB.setText(rb.getString("generate"));
    generateUpcoming.setText(rb.getString("generateUpcoming"));
    generateC.setText(rb.getString("generate"));
    
    introMeet = rb.getString("introMeet");
    followUp = rb.getString("followUp");
    secondFollowUp = rb.getString("secondFollowUp");
    aptSchedule = rb.getString("aptSchedule");
    upcomingApts = rb.getString("upcomingApts");
    noUpcomingApts = rb.getString("noUpcomingApts");
    report1 = rb.getString("report1");
    report2 = rb.getString("report2");
    report3 = rb.getString("report3");
    
    tab5.setText(rb.getString("userName"));
    profileID.setText(rb.getString("profileID"));
    profileName.setText(rb.getString("profileName"));
    nameCheck.setText(rb.getString("nameCheck"));
    nameSave.setText(rb.getString("save"));
    
    tab6.setText(rb.getString("password"));
    newPass.setText(rb.getString("newPass"));
    verifyPass.setText(rb.getString("verifyPass"));
    passCheck.setText(rb.getString("passCheck"));
    passSave.setText(rb.getString("save"));
    
    tab7.setText(rb.getString("language"));
    selectLang.setText(rb.getString("selectLang"));
    english.setText(rb.getString("english"));
    spanish.setText(rb.getString("spanish"));
    french.setText(rb.getString("french"));
    saveLang.setText(rb.getString("save"));
    
    formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", Login.LOCALE);
    formatter2 = DateTimeFormatter.ofPattern("hh:mm a",Login.LOCALE);
    formatter3 = DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy", Login.LOCALE);
    formatter4 = DateTimeFormatter.ofPattern("MM/dd/yyyy", Login.LOCALE);
    }   

    @FXML 
    private void manageCustomer(MouseEvent e){
        tabPane.setSelectionModel(sM);
        tabPane.getSelectionModel().select(tab1);
        tabPane.setSelectionModel(new NullSelection()); 
    }
    
    @FXML 
    private void manageCalendar(MouseEvent e){
        tabPane.setSelectionModel(sM);
        tabPane.getSelectionModel().select(tab2);
        tabPane.setSelectionModel(new NullSelection());
    }
    
    @FXML 
    private void manageApt(MouseEvent e){
        tabPane.setSelectionModel(sM);
        tabPane.getSelectionModel().select(tab3);
        tabPane.setSelectionModel(new NullSelection());
    }
    
    @FXML 
    private void manageProfile(ActionEvent e){
        tabPane.setSelectionModel(sM);
        tabPane.getSelectionModel().select(tab4);
        tabPane.setSelectionModel(new NullSelection());
    }
    
    @FXML 
    private void signOut(ActionEvent event) throws Exception{
         
        Stage stage = (Stage)addCust.getScene().getWindow();
        stage.close();
        Customer.custList.clear(); 
        Appointment.aptList.clear();
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/LoginFXML.fxml"));
        Stage loginStage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loginRoot);
        loginStage.setScene(scene);
        loginStage.show();
        
    }
    
    @FXML 
    private void getDetails(MouseEvent m) throws Exception{
        if(!custTable.getSelectionModel().isEmpty()){
            Customer detail = new Customer( Customer.custList.get(custTable.getSelectionModel().getSelectedIndex()));
            Customer.details.setText(detail.toString());
            Parent detailsRoot = FXMLLoader.load(getClass().getResource("/fxml/Customer.fxml"));
            Stage detailsStage = new Stage();
            Scene scene = new Scene(detailsRoot);
            detailsStage.setScene(scene);
            detailsStage.setResizable(false);
            detailsStage.show();
        }
    }
     
    @FXML 
    private void getAptDetails(MouseEvent m) throws Exception{
        if(!aptTable.getSelectionModel().isEmpty()){
            Appointment.aptDetails.setText(aptTable.getSelectionModel().getSelectedItem().toString());
            Parent detailsRoot = FXMLLoader.load(getClass().getResource("/fxml/Appointment.fxml"));
            Stage detailsStage = new Stage();
            Scene scene = new Scene(detailsRoot);
            detailsStage.setScene(scene);
            detailsStage.setResizable(false);
            detailsStage.show();   
        }
    }
    
    @FXML 
    private void getCancelledAptDetails(MouseEvent m) throws Exception{
        if(!cancelTable.getSelectionModel().isEmpty()){
            Appointment.aptDetails.setText(cancelTable.getSelectionModel().getSelectedItem().toString());
            Parent detailsRoot = FXMLLoader.load(getClass().getResource("/fxml/Appointment.fxml"));
            Stage detailsStage = new Stage();
            Scene scene = new Scene(detailsRoot);
            detailsStage.setScene(scene);
            detailsStage.setResizable(false);
            detailsStage.show();   
        }
    }
    
    @FXML 
    private void getCheckedInAptDetails(MouseEvent m) throws Exception{
        if(!inTable.getSelectionModel().isEmpty()){
            Appointment.aptDetails.setText(inTable.getSelectionModel().getSelectedItem().toString());
            Parent detailsRoot = FXMLLoader.load(getClass().getResource("/fxml/Appointment.fxml"));
            Stage detailsStage = new Stage();
            Scene scene = new Scene(detailsRoot);
            detailsStage.setScene(scene);
            detailsStage.setResizable(false);
            detailsStage.show();   
        }
    }
    
    @FXML 
    private void getAbsentAptDetails(MouseEvent m) throws Exception{
        if(!absentTable.getSelectionModel().isEmpty()){
            Appointment.aptDetails.setText(absentTable.getSelectionModel().getSelectedItem().toString());
            Parent detailsRoot = FXMLLoader.load(getClass().getResource("/fxml/Appointment.fxml"));
            Stage detailsStage = new Stage();
            Scene scene = new Scene(detailsRoot);
            detailsStage.setScene(scene);
            detailsStage.setResizable(false);
            detailsStage.show();   
        }
    }
    @FXML 
    private void checkedIn(ActionEvent event){
    if(!aptTable.getSelectionModel().isEmpty()){
        try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
        
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
        
            int appointmentId = aptTable.getSelectionModel().getSelectedItem().getAptID();
            String del = "DELETE FROM appointment WHERE appointmentId=" + appointmentId;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                alert1, ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES){ 
                Appointment.inList.add(aptTable.getSelectionModel().getSelectedItem());
                Appointment.aptList.remove(aptTable.getSelectionModel().getSelectedItem()); 
                alert.close();
                int rs = stmt.executeUpdate(del);           
            }
        
        }catch(SQLException e){e.printStackTrace();}
    }
    }
     
    @FXML 
    private void cancelled(ActionEvent event){
    if(!aptTable.getSelectionModel().isEmpty()){
        try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
        
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
        
            int appointmentId = aptTable.getSelectionModel().getSelectedItem().getAptID();
            String del = "DELETE FROM appointment WHERE appointmentId=" + appointmentId;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                alert2, ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES){ 
                Appointment.cancelList.add(aptTable.getSelectionModel().getSelectedItem());
                Appointment.aptList.remove(aptTable.getSelectionModel().getSelectedItem()); 
                alert.close();
                int rs = stmt.executeUpdate(del);           
            }
        
        }catch(SQLException e){e.printStackTrace();}
    }
    }
     
    @FXML 
    private void absent(ActionEvent event){
    if(!aptTable.getSelectionModel().isEmpty()){     
        try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
        
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
        
            int appointmentId = aptTable.getSelectionModel().getSelectedItem().getAptID();
            String del = "DELETE FROM appointment WHERE appointmentId=" + appointmentId;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                alert3, ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES){ 
                Appointment.absentList.add(aptTable.getSelectionModel().getSelectedItem());
                Appointment.aptList.remove(aptTable.getSelectionModel().getSelectedItem()); 
                alert.close();
                int rs = stmt.executeUpdate(del);           
            }
        
        }catch(SQLException e){e.printStackTrace();}
    }
    }
     
    @FXML 
    private void searchCustomer( ActionEvent event){
        for (
                Customer c:  Customer.custList){
            if (Integer.toString(c.getCustID()).equals(searchField.getText())){
                custTable.getSelectionModel().select(Customer.custList.indexOf(c));
                searchField.clear();
            }
        }
    }
     
    @FXML 
    private void searchUpcoming( ActionEvent event){
        for (Appointment a:  Appointment.aptList){
            if (Integer.toString(a.getCustID()).equals(searchFieldApt.getText())){
                aptTable.getSelectionModel().select(Appointment.aptList.indexOf(a));
                searchFieldApt.clear();
            }
        }
    }
      
    @FXML 
    private void searchCheckedIn( ActionEvent event){
        for (Appointment a:  Appointment.inList){
            if (Integer.toString(a.getCustID()).equals(searchFieldIn.getText())){
                inTable.getSelectionModel().select(Appointment.inList.indexOf(a));
                searchFieldIn.clear();
            }
        }
    }
       
    @FXML 
    private void searchCancelled( ActionEvent event){
        for (Appointment a:  Appointment.cancelList){
            if (Integer.toString(a.getCustID()).equals(searchFieldCancel.getText())){
                cancelTable.getSelectionModel().select(Appointment.cancelList.indexOf(a));
                searchFieldCancel.clear();
            }
        }
    }
        
    @FXML 
    private void searchAbsent( ActionEvent event){
        for (Appointment a:  Appointment.absentList){
            if (Integer.toString(a.getCustID()).equals(searchFieldAbsent.getText())){
                absentTable.getSelectionModel().select(Appointment.absentList.indexOf(a));
                searchFieldAbsent.clear();
            }
        }
    }
     
    @FXML 
    private void addCustomer(MouseEvent m) throws Exception{
         
        Parent addRoot = FXMLLoader.load(getClass().getResource("/fxml/AddCustomer.fxml"));
        Stage addStage = new Stage();
        Scene scene = new Scene(addRoot);
        addStage.setScene(scene);
        addStage.setResizable(false);
        addStage.show();
            
    }
     
    @FXML 
    private void createAppointment(MouseEvent m) throws Exception{
         
        Parent addRoot = FXMLLoader.load(getClass().getResource("/fxml/CreateAppointment.fxml"));
        Stage addStage = new Stage();
        Scene scene = new Scene(addRoot);
        addStage.setScene(scene);
        addStage.setResizable(false);
        addStage.show();
            
    }
     
    @FXML 
    private void modifyCustomer(MouseEvent m) throws Exception{
        if(!custTable.getSelectionModel().isEmpty()){
         /*
            Customer.custIndex = custTable.getSelectionModel().getSelectedItems().indexOf(
                    custTable.getSelectionModel().getSelectedItem());
         
            I am not sure why but the above code does not work properly 
            even though it is a more specific version of the one below
         */
            Customer.custIndex = custTable.getSelectionModel().getSelectedIndex();
            Parent addRoot = FXMLLoader.load(getClass().getResource("/fxml/ModCustomer.fxml"));
            Stage addStage = new Stage();
            Scene scene = new Scene(addRoot);
            addStage.setScene(scene);
            addStage.setResizable(false);
            addStage.show();
        }
            
    }
    
    @FXML 
    private void updateAppointment(MouseEvent m) throws Exception{
        if(!aptTable.getSelectionModel().isEmpty()){ 
            Appointment.aptIndex = aptTable.getSelectionModel().getSelectedIndex();
            Parent addRoot = FXMLLoader.load(getClass().getResource("/fxml/UpdateApt.fxml"));
            Stage addStage = new Stage();
            Scene scene = new Scene(addRoot);
            addStage.setScene(scene);
            addStage.setResizable(false);
            addStage.show();
        }  
    }
     
    @FXML
    private void deleteCustomer(MouseEvent m){
        if(!custTable.getSelectionModel().isEmpty()){
            try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
        
                Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
        
                int customerId = custTable.getSelectionModel().getSelectedItem().getCustID();
                int addressId = custTable.getSelectionModel().getSelectedItem().getAddressId();
                int cityId = custTable.getSelectionModel().getSelectedItem().getCityId();
                int countryId = custTable.getSelectionModel().getSelectedItem().getCountryId();
        
                String del = "DELETE FROM customer WHERE customerId=" + "'" + customerId +"'";
                String del2 = "DELETE FROM address WHERE addressId=" + "'" + addressId +"'";
                String del3 = "DELETE FROM city WHERE cityId=" + "'" + cityId +"'";
                String del4 = "DELETE FROM country WHERE countryId=" + "'" + countryId +"'";
        
        
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                    alert4, ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES){ 
                    custTable.getItems().removeAll(custTable.getSelectionModel().getSelectedItem()); 
                    alert.close();
                    int rs = stmt.executeUpdate(del);
                    int rs2 = stmt.executeUpdate(del2);
                    int rs3 = stmt.executeUpdate(del3);
                    int rs4 = stmt.executeUpdate(del4);
                } 
            }catch(SQLException e){e.printStackTrace();}  
        }
    }
    
    @FXML
    private void removeAppointment(MouseEvent m){
        if(!aptTable.getSelectionModel().isEmpty()){
            try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
        
                Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
        
                int appointmentId = aptTable.getSelectionModel().getSelectedItem().getAptID();
                String del = "DELETE FROM appointment WHERE appointmentId=" + appointmentId;
  
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                    alert5, ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES){ 
                    aptTable.getItems().removeAll(aptTable.getSelectionModel().getSelectedItem()); 
                    alert.close();
                    int rs = stmt.executeUpdate(del);           
                }
            }catch(SQLException e){e.printStackTrace();}
        }
    }
    
    private void populateCustomer(){
        
        try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
            String join ="SELECT * FROM "
                    + "customer JOIN address ON customer.addressId = address.addressId JOIN city ON address.cityId = city.cityId "
                    + "JOIN country ON city.countryId = country.countryId";
            ResultSet rs =  stmt.executeQuery(join);
            LocalDateTime ldt;
            while(rs.next()) {
            int id = rs.getInt("customerId");
            String name = rs.getString("customerName");
            int active = rs.getInt("active");
            Timestamp custCreateDate = rs.getTimestamp("createDate");
            String custCreatedBy = rs.getString("createdBy");
            Timestamp custLastUpdate = rs.getTimestamp("lastUpdate");
            String custLastUpdateBy = rs.getString("lastUpdateBy");
            String address = rs.getString("address");
            int addressId = rs.getInt("addressId");
            String postal = rs.getString("postalCode");
            String phone = rs.getString("phone");
            String city = rs.getString("city");
            int cityId = rs.getInt("cityId");
            String country = rs.getString("country");
            int countryId = rs.getInt("countryId");
            Customer.custList.add(new Customer(id, name, active, custCreateDate, custCreatedBy, custLastUpdate, 
                    custLastUpdateBy, address, addressId, postal, phone, city, cityId, country, countryId));
        }
        
        }catch (SQLException e){e.printStackTrace();}
    }
    
    private void populateAppointment(){
        
        try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
            Statement stmt1 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs =  stmt.executeQuery("SELECT * FROM appointment");
            ResultSet rs2 =  stmt1.executeQuery("SELECT customerName FROM customer "
                    + "JOIN appointment ON customer.customerId = appointment.customerId");
            while(rs.next() && rs2.next()) {
            int aptId = rs.getInt("appointmentId");
            int customerId = rs.getInt("customerId");
            int userId = rs.getInt("userId");
            String description = rs.getString("description");
            String location = rs.getString("location");
            String contact = rs.getString("contact");
            String type = rs.getString("title");
            LocalTime startTime = rs.getTimestamp("start").toLocalDateTime().toLocalTime();
            LocalDate startDate = rs.getTimestamp("start").toLocalDateTime().toLocalDate();
            LocalTime endTime = rs.getTimestamp("end").toLocalDateTime().toLocalTime();
            Timestamp aptCreateDate = rs.getTimestamp("createDate");
            String aptCreatedBy = rs.getString("createdBy");
            Timestamp aptLastUpdate = rs.getTimestamp("lastUpdate");
            String aptLastUpdateBy = rs.getString("lastUpdateBy");
            String customerName = rs2.getString("customerName");
            
            Appointment.aptList.add(new Appointment(aptId, customerId, userId, description, location, contact, 
                    type, startTime, startDate, endTime, aptCreateDate, aptCreatedBy, aptLastUpdate, aptLastUpdateBy, customerName));
        }
        
        }catch (SQLException e){e.printStackTrace();}
    }
    
    private void setCalendarMonth(){
        calendarMonth.setText(currentDate.getMonth().getDisplayName(TextStyle.FULL, Login.LOCALE) + " " + currentDate.getYear()); 
    }
    
    private void setCalendarWeek(){
        LocalDate weeklyCalendarDate = LocalDate.of(currentDate.getYear(), 
                currentDate.getMonth(),
                currentDate.getDayOfMonth());
        
        while (weeklyCalendarDate.getDayOfWeek().getValue() != 7 ) {
            weeklyCalendarDate = weeklyCalendarDate.minusDays(1);
        }

        String sunday2 = weeklyCalendarDate.format(formatter);
        
        
        while (weeklyCalendarDate.getDayOfWeek().getValue() != 6) {
            weeklyCalendarDate = weeklyCalendarDate.plusDays(1);
        }
        
        String saturday2 = weeklyCalendarDate.format(formatter);
        
        calendarMonth.setText(sunday2 + "  -  " + saturday2);
    }
    
    private void previousMonth() {
        currentDate = currentDate.minusMonths(1);
        populateCalendar(currentDate);
        setCalendarMonth();
    }
    
    private void nextMonth() {
        currentDate = currentDate.plusMonths(1);
        populateCalendar(currentDate);
        setCalendarMonth();
    }
    
    private void previousWeek() {
        currentDate = currentDate.minusWeeks(1);
        populateCalendar(currentDate);
        setCalendarWeek();
        
    }
    
    private void nextWeek() {
        currentDate = currentDate.plusWeeks(1);
        populateCalendar(currentDate);
        setCalendarWeek();
    }
    
    public void populateCalendar(LocalDate localDate) {
    //MonthlyView
        LocalDate monthlyCalendarDate = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 1);
        // Sets day on a sunday before populating table
        while (!monthlyCalendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            monthlyCalendarDate = monthlyCalendarDate.minusDays(1);
        }
  
        for (DatePane dP : MCV.getCalendarDays()) {
            if (!dP.getChildren().isEmpty()) {
                dP.getChildren().remove(0,2);
            }
            Label day = new Label(String.valueOf(monthlyCalendarDate.getDayOfMonth()));
            dP.setDate(monthlyCalendarDate);
            
            dP.getChildren().add(0,day);
  
            DatePane.setTopAnchor(day, 5.0);
            DatePane.setLeftAnchor(day, 5.0);
       
            Label appoint = new Label();
            appoint.setFont(Font.font("Verdana", FontWeight.NORMAL, 9));
            dP.getChildren().add(1, appoint);
            
            DatePane.setTopAnchor(appoint, 25.0);
            DatePane.setLeftAnchor(appoint, 3.0);
            
            int appointmentNumber = 0;
            
            for( Appointment apt: Appointment.aptList){
                if(dP.getDate().equals(apt.getStartDate())){
                   appointmentNumber++; 
                }
            }
            
            if(appointmentNumber != 0){
                appoint.setText(appointmentNumber + " " + calendarApts + " ");
            }
            
             monthlyCalendarDate = monthlyCalendarDate.plusDays(1);
        }
           
        
        for (DatePane dP : MCV.getCalendarDays()) {
            dP.setBackground(new Background(new BackgroundFill(Paint.valueOf("White"), CornerRadii.EMPTY, Insets.EMPTY)));
            dP.setOnMouseClicked(null);
            if(!currentDate.getMonth().equals(dP.getDate().getMonth())){                
                dP.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightgray"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        
        
        for (DatePane dP : MCV.getCalendarDays()) {
            if (currentDate.getMonth().equals(dP.getDate().getMonth())) {
                dP.setOnMouseClicked(e ->{ 
                    populateCalendarTable(dP);
                    setTableHeader(dP);
                    dP.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightblue"), CornerRadii.EMPTY, Insets.EMPTY)));
                    for (DatePane dPs : MCV.getCalendarDays()) {
                        if(!dP.equals(dPs) & currentDate.getMonth().equals(dPs.getDate().getMonth())){
                            dPs.setBackground(new Background(new BackgroundFill(Paint.valueOf("White"), CornerRadii.EMPTY, Insets.EMPTY)));   
                        }
                    }
                });
            }else{
                dP.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightgray"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        
        for (DatePane dP : MCV.getCalendarDays()) {
            if(dP.getDate().equals(LocalDate.now())){  
                populateCalendarTable(dP);
                setTableHeader(dP);
                dP.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightblue"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        
        
    //WeeklyView
        
        LocalDate weeklyCalendarDate = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        while (!weeklyCalendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            weeklyCalendarDate = weeklyCalendarDate.minusDays(1);
        }
        
        for (DatePane dP : WCV.getCalendarDays()) {
            if (!dP.getChildren().isEmpty()) {
                dP.getChildren().remove(0);
            }
            Label day = new Label(String.valueOf(weeklyCalendarDate.getDayOfMonth()));
            dP.setDate(weeklyCalendarDate);
            dP.getChildren().add(day);
            
            DatePane.setTopAnchor(day, 5.0);
            DatePane.setLeftAnchor(day, 5.0);
            
            weeklyCalendarDate = weeklyCalendarDate.plusDays(1);
        }
        
        LocalDate weeklyCalendarDateTime = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        while (!weeklyCalendarDateTime.getDayOfWeek().toString().equals("SUNDAY") ) {
            weeklyCalendarDateTime = weeklyCalendarDateTime.minusDays(1);
        }
        
        LocalTime hours = LocalTime.of(9, 0);
        
        for (TimePane tP : WCV.getCalendarHours()) {
            if (!tP.getChildren().isEmpty()) {
                tP.getChildren().remove(0);
            }
            
           
            tP.setDate(weeklyCalendarDateTime);
            
            tP.setTime(hours);
                         
            Label hour = new Label();
            hour.setFont(Font.font("Verdana", FontWeight.NORMAL, 10));
            hour.setText(hours.format(formatter2));
            tP.getChildren().add(hour);
            
            int appointmentNumber = 0;
            
            for( Appointment apt: Appointment.aptList){
                if(tP.getDate().equals(apt.getStartDate()) & 
                        tP.getTime().equals(apt.getStartTime())){
                   appointmentNumber++; 
                }
            }
            
            if(appointmentNumber != 0){
                hour.setText(hours.format(formatter2) +"\n "+ appointmentNumber + " " + calendarApts);
            }
            
            if(tP.getTime().equals(LocalTime.of(16, 30))){
                hours = LocalTime.of(8, 30);
                weeklyCalendarDateTime = weeklyCalendarDateTime.plusDays(1);
            }
            
            hours = hours.plusMinutes(30);   
        }

        for (GridPane gP : WCV.getWeekList()) {
            gP.setBackground(new Background(new BackgroundFill(Paint.valueOf("White"), CornerRadii.EMPTY, Insets.EMPTY)));
            gP.setOnMouseClicked(null);
        }

        for (GridPane gP : WCV.getWeekList()) {
            gP.setOnMouseClicked(e ->{ 
                        gP.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightblue"), CornerRadii.EMPTY, Insets.EMPTY)));
                        populateCalendarTable((DatePane)gP.getChildren().get(0));
                        setTableHeader((DatePane)gP.getChildren().get(0));
                        for (GridPane gPs : WCV.getWeekList()) {
                            if(!gP.equals(gPs)){
                                gPs.setBackground(new Background(new BackgroundFill(Paint.valueOf("White"), CornerRadii.EMPTY, Insets.EMPTY)));   
                            }
                        }
            });    
            
        }

        for (GridPane gP : WCV.getWeekList()) {
            for (DatePane dP : WCV.getCalendarDays() ) {
                if(dP.getDate().equals(LocalDate.now()) & gP.getChildren().contains(dP)){                
                    gP.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightblue"), CornerRadii.EMPTY, Insets.EMPTY)));
                    populateCalendarTable(dP);
                    setTableHeader(dP);
                }
            }
        }    
    }
    
    private void populateCalendarTable(DatePane dP){
        calendarList.clear();
        for( Appointment apt: Appointment.aptList){
            if(dP.getDate().equals(apt.getStartDate())){
                calendarList.add(apt);
            }
        }
        calendarTable.refresh();
    }
    
    private void setTableHeader(DatePane dP){
        calendarCol.setText(dP.getDate().format(formatter3));
    }
    
     @FXML
    private void switchToMonthlyView(MouseEvent m){
        calendarPane.getChildren().clear();
        calendarPane.getChildren().add(MCV.getCalendar());
        setCalendarMonth();
        next.setOnMouseClicked(null);
        next.setOnMouseClicked(c -> nextMonth());
        previous.setOnMouseClicked(null);
        previous.setOnMouseClicked(c -> previousMonth());
    }
     
     @FXML
    private void switchToWeeklyView(MouseEvent m){
        calendarPane.getChildren().clear();
        calendarPane.getChildren().add(WCV.getCalendar());
        setCalendarWeek();
        next.setOnMouseClicked(null);
        next.setOnMouseClicked(c -> nextWeek());
        previous.setOnMouseClicked(null);
        previous.setOnMouseClicked(c -> previousWeek());
    }
    
    @FXML
    private void appointmentTypeReport(MouseEvent m) {
        
        ArrayList<String> months = new ArrayList<>();
        String reportsText = "";
        Month month = Month.JANUARY;
        for (int j = 0; j < 12; j++) {
            String monthLabel = "\n" + month.getDisplayName(TextStyle.FULL, Login.LOCALE) + ":";
            String type1 = "\n\t" + introMeet + ":";
            int one = 0;
            String type2 = "\n\t" + followUp + ":";
            int two = 0;
            String type3 = "\n\t"+ secondFollowUp + ":";
            int three = 0;
            String type4 = "\n\tN/A:";
            int four = 0;
            for (Appointment apt: Appointment.aptList){
                if(apt.getStartDate().getMonth().equals(month)){
                    ArrayList <Appointment> appoint = new ArrayList<>();
                    appoint.add(apt);
                    for (Appointment ap: appoint){
                        if(ap.getType().equalsIgnoreCase(introMeet)){
                            one++;
                        }else if(ap.getType().equalsIgnoreCase(followUp)){
                            two++;
                        }else if(ap.getType().equalsIgnoreCase(secondFollowUp)){
                            three++;
                        }else {
                            four++;
                        }
                    }
                }
            }
            type1 = type1.concat(" " + one);
            type2 = type2.concat(" " + two);
            type3 = type3.concat(" " + three);
            type4 = type4.concat(" " + four);
            //months.add(monthLabel.concat(type1+type2+type3+type4)); Does not lead to proper display
            months.add(monthLabel); 
            months.add(type1); months.add(type2);
            months.add(type3); months.add(type4);
            reportsText = reportsText.concat(monthLabel+type1+type2+type3+type4);
            month = month.plus(1);
            
        }
        try {
            Path path = Paths.get(report1);
            Files.write(path, months, StandardCharsets.UTF_8);
        }catch (IOException e) {e.printStackTrace();}
        reports.setText(reportsText);
        //reports.setText(months.toString()); Does not Lead t proper Display
    }
    
    @FXML
    private void consultantScheduleReport(MouseEvent m){
        //Code for indefinte number of users
        ArrayList<String> schedule = new ArrayList<>();
        String reportsText = "";
        try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
        
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs = stmt.executeQuery("SELECT userName, userId FROM user");
            while(rs.next()){
                int usersID = rs.getInt("userId");
                String userName = "\n" + rs.getString("userName") + "'s " + aptSchedule + ":";
                schedule.add(userName);
                reportsText = reportsText.concat(userName);
                for(Appointment apt: Appointment.aptList){
                    if(apt.getUserID() == usersID){
                        String mySchedule = "\n\t" +apt.toString3() + "\n";
                        schedule.add(mySchedule);
                        reportsText = reportsText.concat(mySchedule);
                    }
                }   
            }
        
        }catch(SQLException e){e.printStackTrace();}
        try {
            Path path = Paths.get(report2);
            Files.write(path, schedule, StandardCharsets.UTF_8);
        }catch (IOException e) {e.printStackTrace();}
        reports.setText(reportsText);
    }
    
    @FXML
    private void upcomingAppointmentsReport(MouseEvent m){
        //Code for indefinte number of users
        ArrayList<String> upcoming = new ArrayList<>(50);
        String reportsText = "";
        try(Connection conn = DriverManager.getConnection(Login.url,Login.user,Login.pass);){
        
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs = stmt.executeQuery("SELECT userName, userId FROM user");
            while(rs.next()){
                int usersID = rs.getInt("userId");
                String userName = "\n" + rs.getString("userName") + "'s "+ upcomingApts + ":";
                upcoming.add(userName);
                reportsText = reportsText.concat(userName);
                int count = 0;
                for(Appointment apt: Appointment.aptList){
                    if(apt.getUserID() == usersID & apt.getStartDate().equals(LocalDate.now())){
                        count++;
                        String mySchedule = "\n\t" +apt.toString3() + "\n";
                        upcoming.add(mySchedule);
                        reportsText = reportsText.concat(mySchedule);
                    }
                }
                if(count == 0){
                    String none ="\n\t" + noUpcomingApts +"\n";
                    upcoming.add(none);
                    reportsText = reportsText.concat(none);
                } 
            }
        
        }catch(SQLException e){e.printStackTrace();}
        try {
            Path path = Paths.get(report3);
            Files.write(path, upcoming, StandardCharsets.UTF_8);
        }catch (IOException e) {e.printStackTrace();}
        reports.setText(reportsText);
    }
    
    private void loginAlert(){
        for(Appointment apt: Appointment.aptList){
            if(apt.getUserID() == Login.userIDStored){  
                if(LocalTime.now().until(apt.getStartTime(), ChronoUnit.MINUTES) <= 20 &
                    LocalTime.now().until(apt.getStartTime(), ChronoUnit.MINUTES) > 0 &
                        apt.getStartDate().equals(LocalDate.now())){
                        Alert alert = new Alert(Alert.AlertType.NONE, 
                            alert6, ButtonType.OK);
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.OK) {
                            alert.close();
                        } 
                        break;
                }               
            }
        }
    }
    
    @FXML private void enableName(ActionEvent e){
        if(!nameCheck.isSelected()){
            profileNameF.setDisable(true);
        }
        if(nameCheck.isSelected()){
            profileNameF.setDisable(false);
        }
    }
    
    @FXML private void enablePassword(ActionEvent e){
        if(!passCheck.isSelected()){
            newPassF.setDisable(true);
            verifyPassF.setDisable(true);
        }
        if(passCheck.isSelected()){
            newPassF.setDisable(false);
            verifyPassF.setDisable(false);
        }
    }
    
    @FXML private void saveUserName(ActionEvent a){
        try(Connection conn = DriverManager.getConnection( Login.url,Login.user,Login.pass);){
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
           
            int rs1 = stmt.executeUpdate("UPDATE user SET userName ='" + profileNameF.getText()
            + "' WHERE userId =" + Login.userIDStored);
            
            Login.userNameStored = profileNameF.getText();
            userOptions.setText(Login.userNameStored);
            
            Alert alert = new Alert(Alert.AlertType.NONE, 
                alert10, ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {alert.close();}

        }catch(SQLException e){e.printStackTrace();}
    }
    
    @FXML private void savePassword(){
        String password1 = newPassF.getText();
        String password2 = verifyPassF.getText();
        newPassF.clear();
        verifyPassF.clear();
        
        if(password1.equals(password2)){
            try(Connection conn = DriverManager.getConnection( Login.url,Login.user,Login.pass);){
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
           
            int rs1 = stmt.executeUpdate("UPDATE user SET password ='" + password1
            + "' WHERE userId =" + Login.userIDStored);
            
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
    
    @FXML private void saveLanguage(){
        if(english.isSelected()){
            Login.LOCALE = new Locale("en");
            setLanguage();
            Appointment.aptList.forEach(a-> a.setInfo(a.toString2()));
            populateCalendar(currentDate);
            setCalendarWeek();
            setCalendarMonth();
          
        }else if(spanish.isSelected()){
            Login.LOCALE = new Locale("es");
            setLanguage();
            Appointment.aptList.forEach(a-> a.setInfo(a.toString2()));
            populateCalendar(currentDate);
            setCalendarMonth();
            setCalendarWeek();
            
        }else if(french.isSelected()){
            Login.LOCALE = new Locale("fr");
            setLanguage();
            Appointment.aptList.forEach(a-> a.setInfo(a.toString2()));
            populateCalendar(currentDate);
            setCalendarMonth();
            setCalendarWeek();
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
        userOptions.setText(Login.userNameStored);
        profileIDF.setText(Integer.toString(Login.userIDStored));
        
        Comparator<Appointment> byDateTime = new Comparator<Appointment>(){
            @Override
            public int compare(Appointment a1, Appointment a2){
                return a1.getStartDate().atTime(a1.getStartTime()).compareTo(a2.getStartDate().atTime(a2.getStartTime()));
            }
        };
        
    // CODE FOR CUSTOMER TABLE
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("custID")); 
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName")); 
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("custAddress")); 
        custPostalCol.setCellValueFactory(new PropertyValueFactory<>("custPostal")); 
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone")); 
        custCityCol.setCellValueFactory(new PropertyValueFactory<>("custCity")); 
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("custCountry")); 
        

        
        custTable.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if ( custTable.getSelectionModel().isSelected(custTable.getSelectionModel().getSelectedIndex())  ) {
                custTable.getSelectionModel().clearSelection(custTable.getSelectionModel().getSelectedIndex());
                event.consume();
            }
        });
        
        Customer.custList.addListener((ListChangeListener.Change<? extends Customer> changed) -> {
             custTable.refresh();
        });  
        
        populateCustomer();
        custTable.setItems(Customer.custList);
        custTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        custTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    
    //CODE FOR UPCOMING APPOINTMENT
        staffCol.setCellValueFactory(new PropertyValueFactory<>("aptCreatedBy"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact")); 
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellFactory(col -> new TableCell<Appointment, LocalTime>() {
             @Override 
             public void updateItem(LocalTime ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter2.format(ts));
                 }   
             }
        });
        endTimeCol.setCellFactory(col -> new TableCell<Appointment, LocalTime>() {
             @Override 
             public void updateItem(LocalTime ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter2.format(ts));
                 }   
             }
        });
        startDateCol.setCellFactory(col -> new TableCell<Appointment, LocalDate>() {
             @Override 
             public void updateItem(LocalDate ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter4.format(ts));
                 }   
             }
        });
        
        aptTable.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if ( aptTable.getSelectionModel().isSelected(aptTable.getSelectionModel().getSelectedIndex())  ) {
                aptTable.getSelectionModel().clearSelection(aptTable.getSelectionModel().getSelectedIndex());
                event.consume();
            }
        });
        
         Appointment.aptList.addListener((ListChangeListener.Change<? extends Appointment> changed) -> {
             aptTable.refresh();
             populateCalendar(currentDate);
             calendarList.clear(); calendarTable.refresh();
        });  
        
        populateAppointment();
        Appointment.aptList.sort(byDateTime);
        aptTable.setItems(Appointment.aptList);
        aptTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aptTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
    //CODE FOR CHECKED IN TABLE
        staffCol2.setCellValueFactory(new PropertyValueFactory<>("aptCreatedBy"));
        startTimeCol2.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol2.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        startDateCol2.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        locationCol2.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol2.setCellValueFactory(new PropertyValueFactory<>("contact")); 
        typeCol2.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol2.setCellFactory(col -> new TableCell<Appointment, LocalTime>() {
             @Override 
             public void updateItem(LocalTime ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter2.format(ts));
                 }   
             }
        });
        endTimeCol2.setCellFactory(col -> new TableCell<Appointment, LocalTime>() {
             @Override 
             public void updateItem(LocalTime ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter2.format(ts));
                 }   
             }
        });
        startDateCol2.setCellFactory(col -> new TableCell<Appointment, LocalDate>() {
             @Override 
             public void updateItem(LocalDate ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter4.format(ts));
                 }   
             }
        });
        
        inTable.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if ( inTable.getSelectionModel().isSelected(inTable.getSelectionModel().getSelectedIndex())  ) {
                inTable.getSelectionModel().clearSelection(inTable.getSelectionModel().getSelectedIndex());
                event.consume();
            }
        });
        
         Appointment.inList.addListener((ListChangeListener.Change<? extends Appointment> changed) -> {
             inTable.refresh();
        });  
         
        inTable.setItems(Appointment.inList);
        inTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         
        
    //CODE FOR CANCELLED TABLE
        staffCol3.setCellValueFactory(new PropertyValueFactory<>("aptCreatedBy"));
        startTimeCol3.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol3.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        startDateCol3.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        locationCol3.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol3.setCellValueFactory(new PropertyValueFactory<>("contact")); 
        typeCol3.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol3.setCellFactory(col -> new TableCell<Appointment, LocalTime>() {
             @Override 
             public void updateItem(LocalTime ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter2.format(ts));
                 }   
             }
        });
        endTimeCol3.setCellFactory(col -> new TableCell<Appointment, LocalTime>() {
             @Override 
             public void updateItem(LocalTime ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter2.format(ts));
                 }   
             }
        });
        startDateCol3.setCellFactory(col -> new TableCell<Appointment, LocalDate>() {
             @Override 
             public void updateItem(LocalDate ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter4.format(ts));
                 }   
             }
        });
        
        cancelTable.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if ( cancelTable.getSelectionModel().isSelected(cancelTable.getSelectionModel().getSelectedIndex())  ) {
                cancelTable.getSelectionModel().clearSelection(cancelTable.getSelectionModel().getSelectedIndex());
                event.consume();
            }
        });
        
         Appointment.cancelList.addListener((ListChangeListener.Change<? extends Appointment> changed) -> {
             cancelTable.refresh();
        });  
         
        cancelTable.setItems(Appointment.cancelList);
        cancelTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cancelTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         
        
    //CODE FOR ABSENT TABLE
        staffCol4.setCellValueFactory(new PropertyValueFactory<>("aptCreatedBy"));
        startTimeCol4.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol4.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        startDateCol4.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        locationCol4.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol4.setCellValueFactory(new PropertyValueFactory<>("contact")); 
        typeCol4.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol4.setCellFactory(col -> new TableCell<Appointment, LocalTime>() {
             @Override 
             public void updateItem(LocalTime ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter2.format(ts));
                 }   
             }
        });
        endTimeCol4.setCellFactory(col -> new TableCell<Appointment, LocalTime>() {
             @Override 
             public void updateItem(LocalTime ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter2.format(ts));
                 }   
             }
        });
        startDateCol4.setCellFactory(col -> new TableCell<Appointment, LocalDate>() {
             @Override 
             public void updateItem(LocalDate ts, boolean empty) {
                 super.updateItem(ts, empty);
                 if (empty) {
                     setText(null);
                 } else {
                     setText(formatter4.format(ts));
                 }   
             }
        });
        
        absentTable.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if ( absentTable.getSelectionModel().isSelected(absentTable.getSelectionModel().getSelectedIndex())  ) {
                absentTable.getSelectionModel().clearSelection(absentTable.getSelectionModel().getSelectedIndex());
                event.consume();
            }
        });
        
         Appointment.absentList.addListener((ListChangeListener.Change<? extends Appointment> changed) -> {
             absentTable.refresh();
        }); 
         
        absentTable.setItems(Appointment.absentList);
        absentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        absentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
    //Calendar code
        calendarPane.getChildren().add(MCV.getCalendar());
        populateCalendar(currentDate);
        setCalendarMonth();
        next.setOnMouseClicked(c -> nextMonth());
        previous.setOnMouseClicked(c -> previousMonth());
        
    //Calendar Table
        calendarCol.setCellValueFactory(new PropertyValueFactory<>("info"));
        calendarCol.setCellFactory(tc -> {
            TableCell<Appointment, String> cell = new TableCell<>();
            Text info = new Text();
            cell.setGraphic(info);
            info.wrappingWidthProperty().bind(calendarCol.widthProperty());
            info.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        
        calendarTable.setItems(calendarList);
        calendarTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        calendarTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        loginAlert();
        
        sM = tabPane.getSelectionModel();
        tabPane.setSelectionModel(new NullSelection()); 
        
        ToggleGroup radiobuttons = new ToggleGroup();
        english.setToggleGroup(radiobuttons);
        spanish.setToggleGroup(radiobuttons);
        french.setToggleGroup(radiobuttons);
    }   
    
}

