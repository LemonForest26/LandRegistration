package group27.landRegistration.controllers;

import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.*;
import group27.landRegistration.users.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class LogInViewController {
    @javafx.fxml.FXML
    private ComboBox UserTypeCB;
    @javafx.fxml.FXML
    private TextField UserIDTF;
    @javafx.fxml.FXML
    private TextField PasswordTF;

    public void initialize() {
        UserTypeCB.getItems().addAll("Auditor", "Bank Representative", "Land Owner", "Land Registrar", "Public User", "Surveyor");
    }

    @javafx.fxml.FXML
    public void SignUpOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/SignUpView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void SignInOA(ActionEvent actionEvent) {
        try {
            String userType = (String) UserTypeCB.getValue();
            String userID = UserIDTF.getText().trim();
            String password = PasswordTF.getText().trim();

            // ========== BASIC VALIDATION ==========
            if (userType == null || userType.isEmpty()) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Missing User Type", "Please select a user category.");
                return;
            }

            if (userID.isEmpty()) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Missing User ID", "User ID cannot be blank.");
                return;
            }

            if (password.isEmpty()) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Missing Password", "Password cannot be blank.");
                return;
            }

            int enteredID;
            try {
                enteredID = Integer.parseInt(userID);
            } catch (NumberFormatException e) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Invalid ID Format", "User ID must be numeric.");
                return;
            }

            // ========== LOAD USERS ==========
            FileManager<User> file = new FileManager<>("users.dat");
            List<User> allUsers = file.loadList();

            User matchedUser = null;

            // ========== SEARCH FOR USER ==========
            for (User u : allUsers) {
                if (u.getUserID() == enteredID && matchRole(u, userType)) {
                    matchedUser = u;
                    break;
                }
            }

            if (matchedUser == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid Login",
                        "User Not Found",
                        "No account exists with the given ID and role.");
                return;
            }

            // ========== CHECK PASSWORD ==========
            if (!matchedUser.verifyPassword(password)) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid Login",
                        "Wrong Password",
                        "The password you entered is incorrect.");
                return;
            }

            // ========== LOGIN SUCCESS ==========
            CustomAlert.show(Alert.AlertType.INFORMATION, "Login Successful",
                    "Welcome, " + matchedUser.getName(),
                    "You have successfully logged in.");

            // ========== LOAD CORRECT DASHBOARD ==========
            CurrentPageLoader page = new CurrentPageLoader();

            switch (userType) {
                case "Land Owner":
                    page.load("/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml", actionEvent);
                    break;
                case "Land Registrar":
                    page.load("/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml", actionEvent);
                    break;
                case "Surveyor":
                    page.load("/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml", actionEvent);
                    break;
                case "Auditor":
                    page.load("/group27/landRegistration/AllDashboards/AuditorDashboardView.fxml", actionEvent);
                    break;
                case "Public User":
                    page.load("/group27/landRegistration/AllDashboards/PublicDashBoardView.fxml", actionEvent);
                    break;
                case "Bank Representative":
                    page.load("/group27/landRegistration/AllDashboards/BankRepresentativeDashboardView.fxml", actionEvent);
                    break;
            }

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Exception", "Unexpected Error", e.toString());
        }

    }
    private boolean matchRole(User u, String role) {
        switch (role) {
            case "Land Owner": return u instanceof LandOwner;
            case "Land Registrar": return u instanceof LandRegistrar;
            case "Surveyor": return u instanceof Surveyor;
            case "Auditor": return u instanceof Auditor;
            case "Public User": return u instanceof PublicUser;
            case "Bank Representative": return u instanceof BankRepresentative;
            default: return false;
        }
    }

}