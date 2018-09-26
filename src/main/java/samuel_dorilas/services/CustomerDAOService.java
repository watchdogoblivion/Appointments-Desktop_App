package samuel_dorilas.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import samuel_dorilas.Login;
import samuel_dorilas.models.Customer;

public class CustomerDAOService {

	private int CID = 0;
	private int addressId = 0;
	private int cityId = 0;
	private int countryId = 0;
	private final Instant now = Instant.now();
	private final Timestamp createDate = Timestamp.from(now);
	private final Timestamp lastUpdate = Timestamp.from(now);
	private final String createdBy = Login.getUserNameStored();
	private final String lastUpdateBy = Login.getUserNameStored();
	private final String address2 = "";
	private Customer customer;

	public void addCustomer(int active, String custName, String custAddress, String custPostal, String custPhone,
			String custCity, String custCountry) {

		try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);

			String join = "SELECT * FROM "
					+ "customer JOIN address ON customer.addressId = address.addressId JOIN city ON address.cityId = city.cityId "
					+ "JOIN country ON city.countryId = country.countryId";
			ResultSet rs = stmt.executeQuery(join);

			while (rs.next()) {
				if (rs.getInt("customerId") == CID) {
					CID++;
				}
				if (rs.getInt("addressId") == addressId) {
					addressId++;
				}
				if (rs.getInt("cityId") == cityId) {
					cityId++;
				}
				if (rs.getInt("countryId") == countryId) {
					countryId++;
				}
			}

			stmt.executeUpdate("INSERT INTO customer VALUES (" + "'" + CID + "', '" + custName + "', '" + addressId
					+ "', '" + active + "', '" + createDate + "', '" + createdBy + "', '" + lastUpdate + "', '"
					+ lastUpdateBy + "')");

			stmt.executeUpdate("INSERT INTO address VALUES (" + "'" + addressId + "', '" + custAddress + "', '"
					+ address2 + "', '" + cityId + "', '" + custPostal + "', '" + custPhone + "', '" + createDate
					+ "', '" + createdBy + "', '" + lastUpdate + "', '" + lastUpdateBy + "')");

			stmt.executeUpdate("INSERT INTO city VALUES (" + "'" + cityId + "', '" + custCity + "', '" + countryId
					+ "', '" + createDate + "', '" + createdBy + "', '" + lastUpdate + "', '" + lastUpdateBy + "')");

			stmt.executeUpdate("INSERT INTO country VALUES (" + "'" + countryId + "', '" + custCountry + "', '"
					+ createDate + "', '" + createdBy + "', '" + lastUpdate + "', '" + lastUpdateBy + "')");

		} catch (SQLException s) {
			s.printStackTrace();
		}

		customer = new Customer(CID, custName, active, createDate, createdBy, lastUpdate, lastUpdateBy, custAddress,
				addressId, custPostal, custPhone, custCity, cityId, custCountry, countryId);
		Customer.getCustList().add(customer);
	}

	public void modifyCustomer(int active, String custName, String custAddress, String custPostal, String custPhone,
			String custCity, String custCountry) {
		try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);

			stmt.executeUpdate("UPDATE customer SET customerName =" + "'" + custName + "', active ='" + active
					+ "', lastUpdate ='" + lastUpdate + "', lastUpdateBy ='" + lastUpdateBy + "' WHERE customerId ="
					+ Customer.getCustList().get(Customer.getCustIndex()).getCustID());

			stmt.executeUpdate("UPDATE address SET address =" + "'" + custAddress + "', postalCode ='" + custPostal
					+ "', phone ='" + custPhone + "', lastUpdate ='" + lastUpdate + "', lastUpdateBy ='" + lastUpdateBy
					+ "' WHERE addressId =" + Customer.getCustList().get(Customer.getCustIndex()).getAddressId());

			stmt.executeUpdate("UPDATE city SET city =" + "'" + custCity + "', lastUpdate ='" + lastUpdate
					+ "', lastUpdateBy ='" + lastUpdateBy + "' WHERE cityId ="
					+ Customer.getCustList().get(Customer.getCustIndex()).getCityId());

			stmt.executeUpdate("UPDATE country SET country =" + "'" + custCountry + "', lastUpdate ='" + lastUpdate
					+ "', lastUpdateBy ='" + lastUpdateBy + "' WHERE countryId ="
					+ Customer.getCustList().get(Customer.getCustIndex()).getCountryId());

		} catch (SQLException s) {
			s.printStackTrace();
		}
		Customer.getCustList().get(Customer.getCustIndex()).setDisplayed(custName, active, lastUpdate, lastUpdateBy,
				custAddress, custPostal, custPhone, custCity, custCountry);
		Customer.getCustList().add(0, new Customer());
		Customer.getCustList().remove(0);
		// I couldnt find another way to trigger the listener i set on the list since it
		// only responds to adds and removes and not setters. It important in the refreshing
		// of the table
	}

	public void deleteCustomer(TableView<Customer> custTable, String alert4) {
		if (!custTable.getSelectionModel().isEmpty()) {
			try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alert4, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					custTable.getItems().removeAll(custTable.getSelectionModel().getSelectedItem());
					alert.close();

					Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
					int customerId = custTable.getSelectionModel().getSelectedItem().getCustID();
					int addressId = custTable.getSelectionModel().getSelectedItem().getAddressId();
					int cityId = custTable.getSelectionModel().getSelectedItem().getCityId();
					int countryId = custTable.getSelectionModel().getSelectedItem().getCountryId();
					String del = "DELETE FROM customer WHERE customerId=" + "'" + customerId + "'";
					String del2 = "DELETE FROM address WHERE addressId=" + "'" + addressId + "'";
					String del3 = "DELETE FROM city WHERE cityId=" + "'" + cityId + "'";
					String del4 = "DELETE FROM country WHERE countryId=" + "'" + countryId + "'";
					stmt.executeUpdate(del);
					stmt.executeUpdate(del2);
					stmt.executeUpdate(del3);
					stmt.executeUpdate(del4);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void populateCustomer() {

		try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
			String join = "SELECT * FROM "
					+ "customer JOIN address ON customer.addressId = address.addressId JOIN city ON address.cityId = city.cityId "
					+ "JOIN country ON city.countryId = country.countryId";
			ResultSet rs = stmt.executeQuery(join);
			while (rs.next()) {
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
				Customer.getCustList().add(new Customer(id, name, active, custCreateDate, custCreatedBy, custLastUpdate,
						custLastUpdateBy, address, addressId, postal, phone, city, cityId, country, countryId));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
