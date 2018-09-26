/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.models;

import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import samuel_dorilas.Login;

/**
 *
 * @author sj_sc
 */
public class Customer {
	private static Label details = new Label();
    private static int custIndex;
    private static ObservableList<Customer> custList =
        FXCollections.observableArrayList();
    
    private int custID = 0;
    private String custName = "";
    private int active = 0;
    private Timestamp custCreateDate;
    private String custCreatedBy = "";
    private Timestamp custLastUpdate;
    private String custLastUpdateBy = "";
    private String custAddress= "";
    private int addressId = 0;
    private String custPostal= "";
    private String custPhone = "";
    private String custCity= "";
    private int cityId = 0;
    private String custCountry = "";
    private int countryId = 0;
    
  
    private String customerIDText;
    private String customerNameText;
    private String activityText;
    private String createDateText;
    private String createdByText;
    private String lastUpdateDateText;
    private String lastUpdateByText;
    private String addressText;
    private String postalCodeText;
    private String phoneText ;
    private String cityText ;
    private String countryText;

    @Override
    public String toString(){
        
        ResourceBundle rb = ResourceBundle.getBundle("properties/Main", Login.getLOCALE());
        customerIDText = rb.getString("customerID");
        customerNameText = rb.getString("customerName");
        activityText = rb.getString("activity");
        createDateText = rb.getString("createDateText");
        createdByText= rb.getString("createdByText");
        lastUpdateDateText= rb.getString("lastUpdateDateText");
        lastUpdateByText= rb.getString("lastUpdateByText");
        addressText = rb.getString("address");
        postalCodeText = rb.getString("postalCode");
        phoneText = rb.getString("phone");
        cityText = rb.getString("city");
        countryText= rb.getString("country");


        return customerIDText + ": " + custID + "\n" + customerNameText + ": " + custName + "\n" + activityText + ": " + active + 
            "\n" + createDateText + ": " + custCreateDate + "\n" + createdByText + ": " + custCreatedBy + "\n" + lastUpdateDateText + ": " + 
            custLastUpdate + "\n" + lastUpdateByText + ": " + custLastUpdateBy + "\n" + addressText + ": " + 
            custAddress + "\n" + postalCodeText + ": " + custPostal + "\n" + phoneText + ": "+ 
            custPhone + "\n" + cityText + ": " + custCity + "\n" + countryText + ": " + custCountry;
    }
 
    public Customer() {
        this(0, "", 0, null,"",null,"","", 0, "", "", "", 0, "", 0);
    }
 
    public Customer(int custID, String custName, int active, Timestamp custCreateDate, 
            String custCreatedBy, Timestamp custLastUpdate, String custLastUpdateBy, String custAddress, int addressId,
            String custPostal, String custPhone, String custCity, int cityId, String custCountry, int countryId) {
        
        setCustID(custID);
        setCustName(custName);
        setActive(active);
        setCustCreateDate(custCreateDate);
        setCustCreatedBy(custCreatedBy);
        setCustLastUpdate(custLastUpdate);
        setCustLastUpdateBy(custLastUpdateBy);
        setCustAddress(custAddress);
        setAddressId(addressId);
        setCustPostal(custPostal);
        setCustPhone(custPhone);
        setCustCity(custCity);
        setCityId(cityId);
        setCustCountry(custCountry);
        setCountryId(countryId);
        
    }

    public Customer(Customer c){
        setCustID(c.getCustID());
        setCustName(c.getCustName());
        setActive(c.getActive());
        setCustCreateDate(c.getCustCreateDate());
        setCustCreatedBy(c.getCustCreatedBy());
        setCustLastUpdate(c.getCustLastUpdate());
        setCustLastUpdateBy(c.getCustLastUpdateBy());
        setCustAddress(c.getCustAddress());
        setCustPostal(c.getCustPostal());
        setCustPhone(c.getCustPhone());
        setCustCity(c.getCustCity());
        setCustCountry(c.getCustCountry());
    }
    
    public int getCustID() 
    {return custID;}
 
    public void  setCustID(int id) 
    {custID = id;}
        
    public String getCustName() 
    {return custName;}
    
    public void setCustName(String n) 
    {custName = n;}
    
     public int getActive() 
    {return active;}
    
    public void setActive(int n) 
    {active = n;}
    
     public Timestamp getCustCreateDate() 
    {return custCreateDate;}
    
    public void setCustCreateDate(Timestamp n) 
    {custCreateDate = n;}
    
    public String getCustCreatedBy() 
    {return custCreatedBy;}
    
    public void setCustCreatedBy(String createdBy) 
    {custCreatedBy = createdBy;}
    
    public Timestamp getCustLastUpdate() 
    {return custLastUpdate;}
    
    public void setCustLastUpdate(Timestamp n) 
    {custLastUpdate = n;}
    
    public String getCustLastUpdateBy() 
    {return custLastUpdateBy;}
    
    public void setCustLastUpdateBy(String createdBy) 
    {custLastUpdateBy = createdBy;}
    
    public String getCustAddress() 
    {return custAddress;}
    
    public void setCustAddress(String address) 
    {custAddress = address;}
    
    public String getCustPostal() 
    {return custPostal;}
    
    public void setCustPostal(String p) 
    {custPostal = p;}
    
    public String getCustPhone() 
    {return custPhone;}
    
    public void setCustPhone(String m) 
    {custPhone = m;}
    
    public String getCustCity() 
    {return custCity;}
       
    public void setCustCity(String m) 
    {custCity = m;}
    
    public String getCustCountry() 
    {return custCountry;}
       
    public void setCustCountry(String m) 
    {custCountry = m;}
     
    public int getAddressId() 
    {return addressId;}
       
    public void setAddressId(int m) 
    {addressId = m;}
     
    public int getCityId() 
    {return cityId;}
       
    public void setCityId(int m) 
    {cityId = m;}
     
    public int getCountryId() 
    {return countryId;}
       
    public void setCountryId(int m) 
    {countryId = m;}
     
    public void setAll(int custID, String custName, int active, Timestamp custCreateDate, 
            String custCreatedBy, Timestamp custLastUpdate, String custLastUpdateBy, String custAddress, int addressId,
            String custPostal, String custPhone, String custCity, int cityId, String custCountry, int countryId) {
        
        setCustID(custID);
        setCustName(custName);
        setActive(active);
        setCustCreateDate(custCreateDate);
        setCustCreatedBy(custCreatedBy);
        setCustLastUpdate(custLastUpdate);
        setCustLastUpdateBy(custLastUpdateBy);
        setCustAddress(custAddress);
        setAddressId(addressId);
        setCustPostal(custPostal);
        setCustPhone(custPhone);
        setCustCity(custCity);
        setCityId(cityId);
        setCustCountry(custCountry);
        setCountryId(countryId);    
    }
   
    public void setDisplayed( String custName, int active, Timestamp custLastUpdate, String custLastUpdateBy, String custAddress,
            String custPostal, String custPhone, String custCity, String custCountry) {

        setCustName(custName);
        setActive(active);
        setCustLastUpdate(custLastUpdate);
        setCustLastUpdateBy(custLastUpdateBy);
        setCustAddress(custAddress);
        setCustPostal(custPostal);
        setCustPhone(custPhone);
        setCustCity(custCity);
        setCustCountry(custCountry);   
    }

	public static ObservableList<Customer> getCustList() {
		return custList;
	}

	public static void setCustList(ObservableList<Customer> custList) {
		Customer.custList = custList;
	}

	public static int getCustIndex() {
		return custIndex;
	}

	public static void setCustIndex(int custIndex) {
		Customer.custIndex = custIndex;
	}

	public static Label getDetails() {
		return details;
	}

	public static void setDetails(Label details) {
		Customer.details = details;
	}
}
