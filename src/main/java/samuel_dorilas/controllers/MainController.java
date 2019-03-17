/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.controllers;


import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
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
import samuel_dorilas.Login;
import samuel_dorilas.models.Appointment;
import samuel_dorilas.models.Customer;
import samuel_dorilas.models.DatePane;
import samuel_dorilas.models.NullSelection;
import samuel_dorilas.models.TimePane;
import samuel_dorilas.services.AppointmentDAOService;
import samuel_dorilas.services.CustomerDAOService;
import samuel_dorilas.services.ReportService;
import samuel_dorilas.services.UserDAOService;
import samuel_dorilas.views.MonthlyCalendarView;
import samuel_dorilas.views.WeeklyCalendarView;


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
	private SingleSelectionModel<Tab>  sM;
    
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
    
    UserDAOService uDAOS = new UserDAOService();
    CustomerDAOService cDAOS = new CustomerDAOService();
    AppointmentDAOService aDAOS = new AppointmentDAOService();
	ReportService reportService = new ReportService();
    
    private void setLanguage(){
        
    ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
    
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
    
    formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", Login.getLOCALE());
    formatter2 = DateTimeFormatter.ofPattern("hh:mm a",Login.getLOCALE());
    formatter3 = DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy", Login.getLOCALE());
    formatter4 = DateTimeFormatter.ofPattern("MM/dd/yyyy", Login.getLOCALE());
    }   

    private void manage(SingleSelectionModel<Tab> sM, Tab tab, NullSelection nullSelection) {
    	tabPane.setSelectionModel(sM);
        tabPane.getSelectionModel().select(tab);
        tabPane.setSelectionModel(nullSelection); 
    }
    
    @FXML 
    private void manageCustomer(MouseEvent e){
        manage(sM, tab1, new NullSelection());
    }
    
    @FXML 
    private void manageCalendar(MouseEvent e){
    	manage(sM, tab2, new NullSelection());
    }
    
    @FXML 
    private void manageApt(MouseEvent e){
    	manage(sM, tab3, new NullSelection());
    }
    
    @FXML 
    private void manageProfile(ActionEvent e){
    	manage(sM, tab4, new NullSelection());
    }
    
    @FXML 
    private void signOut(ActionEvent event) throws Exception{
         
        Stage stage = (Stage)addCust.getScene().getWindow();
        stage.close();
        Customer.getCustList().clear(); 
        Appointment.getAptList().clear();
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/LoginFXML.fxml"));
        Stage loginStage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loginRoot);
        loginStage.setScene(scene);
        loginStage.show();
        
    }
    
    private void createStage(String resource) throws Exception{
    	Parent detailsRoot = FXMLLoader.load(getClass().getResource(resource));
        Stage detailsStage = new Stage();
        Scene scene = new Scene(detailsRoot);
        detailsStage.setScene(scene);
        detailsStage.setResizable(false);
        detailsStage.show();
    }
    
    @FXML 
    private void getDetails(MouseEvent m) throws Exception{
        if(!custTable.getSelectionModel().isEmpty()){
            Customer detail = new Customer( Customer.getCustList().get(custTable.getSelectionModel().getSelectedIndex()));
            Customer.getDetails().setText(detail.toString());
            createStage("/fxml/Customer.fxml");
        }
    }
     
    @FXML 
    private void getAptDetails(MouseEvent m) throws Exception{
        if(!aptTable.getSelectionModel().isEmpty()){
            Appointment.getAptDetails().setText(aptTable.getSelectionModel().getSelectedItem().toString());
            createStage("/fxml/Appointment.fxml");  
        }
    }
    
    @FXML 
    private void getCancelledAptDetails(MouseEvent m) throws Exception{
        if(!cancelTable.getSelectionModel().isEmpty()){
            Appointment.getAptDetails().setText(cancelTable.getSelectionModel().getSelectedItem().toString());
            createStage("/fxml/Appointment.fxml");   
        }
    }
    
    @FXML 
    private void getCheckedInAptDetails(MouseEvent m) throws Exception{
        if(!inTable.getSelectionModel().isEmpty()){
            Appointment.getAptDetails().setText(inTable.getSelectionModel().getSelectedItem().toString());
            createStage("/fxml/Appointment.fxml");    
        }
    }
    
    @FXML 
    private void getAbsentAptDetails(MouseEvent m) throws Exception{
        if(!absentTable.getSelectionModel().isEmpty()){
            Appointment.getAptDetails().setText(absentTable.getSelectionModel().getSelectedItem().toString());
            createStage("/fxml/Appointment.fxml");   
        }
    }
    @FXML 
    private void checkedIn(ActionEvent event){
    	aDAOS.checkIn(aptTable, alert1);
    }
     
    @FXML 
    private void cancelled(ActionEvent event){
    	aDAOS.cancelled(aptTable, alert2);
    }
     
    @FXML 
    private void absent(ActionEvent event){
    	aDAOS.absent(aptTable, alert3);
    }
     
    @FXML 
    private void searchCustomer( ActionEvent event){
        for (Customer c:  Customer.getCustList()){
            if (Integer.toString(c.getCustID()).equals(searchField.getText())){
                custTable.getSelectionModel().select(Customer.getCustList().indexOf(c));
                searchField.clear();
            }
        }
    }
     
    @FXML 
    private void searchUpcoming( ActionEvent event){
        for (Appointment a:  Appointment.getAptList()){
            if (Integer.toString(a.getCustID()).equals(searchFieldApt.getText())){
                aptTable.getSelectionModel().select(Appointment.getAptList().indexOf(a));
                searchFieldApt.clear();
            }
        }
    }
      
    @FXML 
    private void searchCheckedIn( ActionEvent event){
        for (Appointment a:  Appointment.getInList()){
            if (Integer.toString(a.getCustID()).equals(searchFieldIn.getText())){
                inTable.getSelectionModel().select(Appointment.getInList().indexOf(a));
                searchFieldIn.clear();
            }
        }
    }
       
    @FXML 
    private void searchCancelled( ActionEvent event){
        for (Appointment a:  Appointment.getCancelList()){
            if (Integer.toString(a.getCustID()).equals(searchFieldCancel.getText())){
                cancelTable.getSelectionModel().select(Appointment.getCancelList().indexOf(a));
                searchFieldCancel.clear();
            }
        }
    }
        
    @FXML 
    private void searchAbsent( ActionEvent event){
        for (Appointment a:  Appointment.getAbsentList()){
            if (Integer.toString(a.getCustID()).equals(searchFieldAbsent.getText())){
                absentTable.getSelectionModel().select(Appointment.getAbsentList().indexOf(a));
                searchFieldAbsent.clear();
            }
        }
    }
     
    @FXML 
    private void addCustomer(MouseEvent m) throws Exception{
        createStage("/fxml/AddCustomer.fxml");     
    }
     
    @FXML 
    private void createAppointment(MouseEvent m) throws Exception{
    	createStage("/fxml/CreateAppointment.fxml");
    }
     
    @FXML 
    private void modifyCustomer(MouseEvent m) throws Exception{
        if(!custTable.getSelectionModel().isEmpty()){
            Customer.setCustIndex(custTable.getSelectionModel().getSelectedIndex());
            createStage("/fxml/ModCustomer.fxml");
        }
            
    }
    
    @FXML 
    private void updateAppointment(MouseEvent m) throws Exception{
        if(!aptTable.getSelectionModel().isEmpty()){ 
            Appointment.setAptIndex(aptTable.getSelectionModel().getSelectedIndex());
            createStage("/fxml/UpdateApt.fxml");
        }  
    }
     
    @FXML
    private void deleteCustomer(MouseEvent m){
        cDAOS.deleteCustomer(custTable, alert4);
    }
    
    @FXML
    private void removeAppointment(MouseEvent m){
       aDAOS.removeAppointment(aptTable, alert5);
    }
    
    private void setCalendarMonth(){
        calendarMonth.setText(currentDate.getMonth().getDisplayName(TextStyle.FULL, Login.getLOCALE()) + " " + currentDate.getYear()); 
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
            
            for( Appointment apt: Appointment.getAptList()){
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
            
            for( Appointment apt: Appointment.getAptList()){
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
        for( Appointment apt: Appointment.getAptList()){
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
    	reportService.appointmentTypeReport(introMeet, followUp, secondFollowUp, report1, reports);
    }
    
    @FXML
    private void consultantScheduleReport(MouseEvent m){
    	reportService.consultantScheduleReport(aptSchedule, report2, reports);
    }
    
    @FXML
    private void upcomingAppointmentsReport(MouseEvent m){
    	reportService.upcomingAppointmentsReport(upcomingApts, noUpcomingApts, report3, reports);
    }
    
    private void loginAlert(){
        for(Appointment apt: Appointment.getAptList()){
            if(apt.getUserID() == Login.getUserIDStored()){  
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
    	uDAOS.saveUserName(profileNameF, userOptions, alert10);
    }
    
    @FXML private void savePassword(){
    	uDAOS.savePassword(newPassF, verifyPassF, alert11, alert12);
    }
    
    @FXML private void saveLanguage(){
        if(english.isSelected()){
            Login.setLOCALE(new Locale("en"));
            setLanguage();
            Appointment.getAptList().forEach(a-> a.setInfo(a.toString2()));
            populateCalendar(currentDate);
            setCalendarWeek();
            setCalendarMonth();
          
        }else if(spanish.isSelected()){
            Login.setLOCALE(new Locale("es"));
            setLanguage();
            Appointment.getAptList().forEach(a-> a.setInfo(a.toString2()));
            populateCalendar(currentDate);
            setCalendarMonth();
            setCalendarWeek();
            
        }else if(french.isSelected()){
            Login.setLOCALE(new Locale("fr"));
            setLanguage();
            Appointment.getAptList().forEach(a-> a.setInfo(a.toString2()));
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
        userOptions.setText(Login.getUserNameStored());
        profileIDF.setText(Integer.toString(Login.getUserIDStored()));
        
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
        
        Customer.getCustList().addListener((ListChangeListener.Change<? extends Customer> changed) -> {
             custTable.refresh();
        });  
        
        cDAOS.populateCustomer();
        custTable.setItems(Customer.getCustList());
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
        
         Appointment.getAptList().addListener((ListChangeListener.Change<? extends Appointment> changed) -> {
             aptTable.refresh();
             populateCalendar(currentDate);
             calendarList.clear(); calendarTable.refresh();
        });  
        
        aDAOS.populateAppointment();
        Appointment.getAptList().sort(byDateTime);
        aptTable.setItems(Appointment.getAptList());
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
        
         Appointment.getInList().addListener((ListChangeListener.Change<? extends Appointment> changed) -> {
             inTable.refresh();
        });  
         
        inTable.setItems(Appointment.getInList());
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
        
         Appointment.getCancelList().addListener((ListChangeListener.Change<? extends Appointment> changed) -> {
             cancelTable.refresh();
        });  
         
        cancelTable.setItems(Appointment.getCancelList());
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
        
         Appointment.getAbsentList().addListener((ListChangeListener.Change<? extends Appointment> changed) -> {
             absentTable.refresh();
        }); 
         
        absentTable.setItems(Appointment.getAbsentList());
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

