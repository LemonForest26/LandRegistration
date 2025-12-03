package group27.landRegistration.controllers;

import group27.landRegistration.users.*;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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

            // ====== 1. EMPTY FIELD VALIDATION ======
            if (NIDText.isEmpty() || phoneText.isEmpty() || email.isEmpty()
                    || username.isEmpty() || password.isEmpty()
                    || gender == null || userType == null || DoB == null) {

                CustomAlert.show(Alert.AlertType.ERROR, "Missing Information",
                        "Unfilled Entry",
                        "Please fill up all required fields.");
                return;
            }

            // ====== 2. NID VALIDATION ======
            try {
                Long.parseLong(NIDText);
            } catch (NumberFormatException e) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid NID",
                        "NID Must Be Numeric",
                        "NID accepts only digits.");
                return;
            }

            if (!(NIDText.length() == 10 || NIDText.length() == 17)) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid NID Length",
                        "Incorrect NID Length",
                        "NID must be exactly 10 or 17 digits.");
                return;
            }

            // ====== 3. PHONE VALIDATION ======
            try {
                Long.parseLong(phoneText);
            } catch (NumberFormatException e) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid Phone",
                        "Phone Must Be Numeric",
                        "Phone number accepts only digits.");
                return;
            }

            if (phoneText.length() != 10) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid Phone Length",
                        "Incorrect Phone Length",
                        "Phone number must be exactly 11 digits (BD format).");
                return;
            }

            // ====== 4. AGE VALIDATION ======
            int age = Period.between(DoB, LocalDate.now()).getYears();
            if (age < 18) {
                CustomAlert.show(Alert.AlertType.ERROR, "Age Restriction",
                        "Invalid Age",
                        "You must be at least 18 years old.");
                return;
            }

            // ====== 5. EMAIL VALIDATION ======
            if (!email.matches("^(.+)@(.+)$")) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid Email",
                        "Incorrect Email Format",
                        "Please provide a valid email address.");
                return;
            }

            // ====== 6. LOAD USERS AND CHECK DUPLICATES ======
            FileManager<User> userFile = new FileManager<>("users.dat");
            List<User> users = userFile.loadList();

            for (User u : users) {
                if (u.getNID() == Long.parseLong(NIDText)) {
                    CustomAlert.show(Alert.AlertType.ERROR, "Duplicate Entry",
                            "NID Already Registered",
                            "This NID is already associated with an account.");
                    return;
                }
                if (u.getEmail().equalsIgnoreCase(email)) {
                    CustomAlert.show(Alert.AlertType.ERROR, "Duplicate Entry",
                            "Email Already Registered",
                            "This email already has an account.");
                    return;
                }
            }

            // ====== 7. CREATE NEW USER OBJECT ======
            User newUser;

            switch (userType) {
                case "Land Owner":
                    newUser = new LandOwner(username, password, email, gender,
                            Long.parseLong(NIDText), Long.parseLong(phoneText), DoB);
                    break;

                case "Land Registrar":
                    newUser = new LandRegistrar(username, password, email, gender,
                            Long.parseLong(NIDText), Long.parseLong(phoneText), DoB);
                    break;

                case "Surveyor":
                    newUser = new Surveyor(username, password, email, gender,
                            Long.parseLong(NIDText), Long.parseLong(phoneText), DoB);
                    break;

                case "Auditor":
                    newUser = new Auditor(username, password, email, gender,
                            Long.parseLong(NIDText), Long.parseLong(phoneText), DoB);
                    break;

                case "Public User":
                    newUser = new PublicUser(username, password, email, gender,
                            Long.parseLong(NIDText), Long.parseLong(phoneText), DoB);
                    break;

                case "Bank Representative":
                    newUser = new BankRepresentative(username, password, email, gender,
                            Long.parseLong(NIDText), Long.parseLong(phoneText), DoB);
                    break;

                default:
                    CustomAlert.show(Alert.AlertType.ERROR, "Error",
                            "Invalid User Type", "Please select a proper user role.");
                    return;
            }


            // ====== 8. SAVE TO FILE ======
            userFile.appendItem(newUser);

            // ====== 9. SUCCESS ALERT ======
            CustomAlert.show(Alert.AlertType.INFORMATION, "Success",
                    "Account Created",
                    "Your account has been created successfully!" + "\nYour user ID is: " + newUser.getUserID() + "\nPassword is: " + newUser.getPassword());

            // ====== 10. GO BACK TO LOGIN ======
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LogInView.fxml", actionEvent);

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Exception",
                    "Unexpected Error",
                    e.toString());
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
