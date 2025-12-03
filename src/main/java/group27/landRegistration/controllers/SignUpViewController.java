package group27.landRegistration.controllers;

import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.Period;

public class SignUpViewController {

    @javafx.fxml.FXML
    private DatePicker DoBDP;
    @javafx.fxml.FXML
    private TextField NIDTF;
    @javafx.fxml.FXML
    private TextField PhoneNumberTF;
    @javafx.fxml.FXML
    private TextField EmailTF;
    @javafx.fxml.FXML
    private TextField UserNameTF;
    @javafx.fxml.FXML
    private ComboBox<String> UserCB;
    @javafx.fxml.FXML
    private TextField PasswordTF;
    @javafx.fxml.FXML
    private ComboBox<String> GenderCB;

    public void initialize() {
        UserCB.getItems().addAll("Auditor", "Bank Representative", "Land Owner", "Land Registrar", "Public User", "Surveyor");
        GenderCB.getItems().addAll("Male", "Female");
    }

    @javafx.fxml.FXML
    public void SignUpOA(ActionEvent actionEvent) {
        try {
            String NIDText = NIDTF.getText();
            String phoneText = PhoneNumberTF.getText();
            String email = EmailTF.getText();
            String username = UserNameTF.getText();
            String password = PasswordTF.getText();
            String gender = GenderCB.getValue();
            String userType = UserCB.getValue();
            LocalDate DoB = DoBDP.getValue();

            if (NIDText.isEmpty() || phoneText.isEmpty() || email.isEmpty() || username.isEmpty()
                    || password.isEmpty() || gender == null || userType == null || DoB == null) {

                CustomAlert.show(Alert.AlertType.ERROR, "Error Alert!", "Unfilled entry!", "Phone fill up all the required field.");
                return;
            }

            try {
                long NID = Long.parseLong(NIDText);
                if (NIDText.length() != 10 || NIDText.length() != 17) {
                    CustomAlert.show(Alert.AlertType.ERROR, "Error Alert!", "Invalid NID number length", "NID number only accepts 10 or 17 digit numbers.");
                    return;
                }
            } catch (NumberFormatException e) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error Alert!", e.toString(), "NID only accepts digit.");
                return;
            }

            try {
                long phone = Long.parseLong(phoneText);
            } catch (NumberFormatException e) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error Alert!", e.toString(), "Phone number only accepts digit.");
                return;
            }

            if (phoneText.length() != 10) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error Alert!", "Invalid phone number length", "Phone number only accepts 10 digit numbers.");
                return;
            }

            LocalDate today = LocalDate.now();
            int age = Period.between(DoB, today).getYears();

            if (age < 18) {
                CustomAlert.show(Alert.AlertType.ERROR, "Minor Alert!", "Invalid age!", "Age must be at least 18 years!.");
                return;
            }

            if (!email.matches("^(.+)@(.+)$")) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error Alert!", "Invalid email format!", "Please provide a valid email address.");
                return;
            }
            String a = null;
        }

        catch(Exception e) {

        }
    }

    @javafx.fxml.FXML
    public void GoBackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LogInView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
