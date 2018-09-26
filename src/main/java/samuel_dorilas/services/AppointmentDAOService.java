package samuel_dorilas.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import samuel_dorilas.Login;
import samuel_dorilas.models.Appointment;

public class AppointmentDAOService {

	private int AID = 0;
	private final int userID = Login.getUserIDStored();
	private final Instant now = Instant.now();
	private final Timestamp aptCreateDate = Timestamp.from(now);
	private final Timestamp aptLastUpdate = Timestamp.from(now);
	private final String aptCreatedBy = Login.getUserNameStored();
	private final String aptLastUpdateBy = Login.getUserNameStored();
	private String custName = "";
	private Appointment appointment;
	private final String url = "N/A";

	public void createAppointment(LocalDate startDateData, LocalTime startTimeData, LocalTime endTimeData, Timestamp startDateTime,
			Timestamp endDateTime, String custID, String menuType, String description, String menuLocation, String contact) {

		try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
			ResultSet rs = stmt.executeQuery("SELECT appointmentId FROM appointment");

			while (rs.next()) {
				if (rs.getInt("appointmentId") == AID) {
					AID++;
				}
			}

			ResultSet rs1 = stmt.executeQuery("SELECT customerName FROM customer WHERE customerId=" + custID);
			rs1.next();
			custName = rs1.getString("customerName");

			stmt.executeUpdate("INSERT INTO appointment VALUES (" + AID + ", " + custID + ", '" + menuType + "', '"
					+ description + "', '" + menuLocation + "', '" + contact + "', '" + url + "', '" + startDateTime
					+ "', '" + endDateTime + "', '" + aptCreateDate + "', '" + aptCreatedBy + "', '" + aptLastUpdate
					+ "', '" + aptLastUpdateBy + "', " + userID + ")");

			appointment = new Appointment(AID, Integer.parseInt(custID), userID, description, menuLocation, contact,
					menuType, startTimeData, startDateData, endTimeData, aptCreateDate, aptCreatedBy, aptLastUpdate,
					aptLastUpdateBy, custName);
			Appointment.getAptList().add(appointment);

		} catch (SQLException s) {
			s.printStackTrace();
		}

	}

	public void updateAppointment(String menuType, String description, String menuLocation, String contact,
			LocalTime startTimeData, LocalDate startDateData, LocalTime endTimeData, Timestamp startDateTime,
			Timestamp endDateTime, Button cancelBtn) {
		try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);

			stmt.executeUpdate("UPDATE appointment SET title =" + "'" + menuType + "', description ='" + description
					+ "', location ='" + menuLocation + "', contact ='" + contact + "', start ='" + startDateTime
					+ "', end ='" + endDateTime + "', lastUpdate ='" + aptLastUpdate + "', lastUpdateBy ='"
					+ aptLastUpdateBy + "' WHERE appointmentId ="
					+ Appointment.getAptList().get(Appointment.getAptIndex()).getAptID());

		} catch (SQLException s) {
			s.printStackTrace();
			((Stage) (cancelBtn.getScene().getWindow())).close();
		}
		Appointment.getAptList().get(Appointment.getAptIndex()).setDisplayed(description, menuLocation, contact,
				menuType, startTimeData, startDateData, endTimeData, aptLastUpdate, aptLastUpdateBy);
		Appointment.getAptList().add(0, new Appointment());
		Appointment.getAptList().remove(0);
	}

	public void checkIn(TableView<Appointment> aptTable, String alert1) {
		if (!aptTable.getSelectionModel().isEmpty()) {
			try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alert1, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					Appointment.getInList().add(aptTable.getSelectionModel().getSelectedItem());
					Appointment.getAptList().remove(aptTable.getSelectionModel().getSelectedItem());
					alert.close();

					Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
					int appointmentId = aptTable.getSelectionModel().getSelectedItem().getAptID();
					String del = "DELETE FROM appointment WHERE appointmentId=" + appointmentId;
					stmt.executeUpdate(del);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void cancelled(TableView<Appointment> aptTable, String alert2) {
		if (!aptTable.getSelectionModel().isEmpty()) {
			try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alert2, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					Appointment.getCancelList().add(aptTable.getSelectionModel().getSelectedItem());
					Appointment.getAptList().remove(aptTable.getSelectionModel().getSelectedItem());
					alert.close();

					Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
					int appointmentId = aptTable.getSelectionModel().getSelectedItem().getAptID();
					String del = "DELETE FROM appointment WHERE appointmentId=" + appointmentId;
					stmt.executeUpdate(del);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void absent(TableView<Appointment> aptTable, String alert3) {
		if (!aptTable.getSelectionModel().isEmpty()) {
			try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alert3, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					Appointment.getAbsentList().add(aptTable.getSelectionModel().getSelectedItem());
					Appointment.getAptList().remove(aptTable.getSelectionModel().getSelectedItem());
					alert.close();

					Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
					int appointmentId = aptTable.getSelectionModel().getSelectedItem().getAptID();
					String del = "DELETE FROM appointment WHERE appointmentId=" + appointmentId;
					stmt.executeUpdate(del);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeAppointment(TableView<Appointment> aptTable, String alert5) {
		if (!aptTable.getSelectionModel().isEmpty()) {
			try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {

				Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);

				int appointmentId = aptTable.getSelectionModel().getSelectedItem().getAptID();
				String del = "DELETE FROM appointment WHERE appointmentId=" + appointmentId;

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alert5, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					aptTable.getItems().removeAll(aptTable.getSelectionModel().getSelectedItem());
					alert.close();
					stmt.executeUpdate(del);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void populateAppointment() {

		try (Connection conn = DriverManager.getConnection(Login.getUrl(), Login.getUser(), Login.getPass());) {
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
			Statement stmt1 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM appointment");
			ResultSet rs2 = stmt1.executeQuery("SELECT customerName FROM customer "
					+ "JOIN appointment ON customer.customerId = appointment.customerId");
			while (rs.next() && rs2.next()) {
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

				Appointment.getAptList()
						.add(new Appointment(aptId, customerId, userId, description, location, contact, type, startTime,
								startDate, endTime, aptCreateDate, aptCreatedBy, aptLastUpdate, aptLastUpdateBy,
								customerName));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
