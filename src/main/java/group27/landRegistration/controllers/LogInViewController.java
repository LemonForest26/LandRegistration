package group27.landRegistration.controllers;

import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.*;
import group27.landRegistration.users.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;

public class LogInViewController {

    @javafx.fxml.FXML
    private TextField UserIDTF;
    @javafx.fxml.FXML
    private TextField PasswordTF;

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
            String userID = UserIDTF.getText().trim();
            String password = PasswordTF.getText().trim();

            // ========== BASIC VALIDATION ==========
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

            User matchedUser = allUsers.stream().filter(u -> u.getUserID() == enteredID).findFirst().orElse(null);

            // ========== SEARCH FOR USER BASED ON ID ==========

            if (matchedUser == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid Login",
                        "User Not Found",
                        "No account exists with the given ID.");
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

            // ========== LOAD DASHBOARD AUTOMATICALLY BASED ON USER CLASS ==========
            CurrentPageLoader page = new CurrentPageLoader();
            String fxmlPath = getDashboardForUser(matchedUser);

            if (fxmlPath != null) {
                page.loadWithData(
                        fxmlPath,
                        actionEvent,
                        controller -> {
                            try {
                                controller.getClass()
                                        .getMethod("setUserData", User.class)
                                        .invoke(controller, matchedUser);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            } else {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Unknown User Type",
                        "Cannot determine dashboard for user.");
            }

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Exception", "Unexpected Error", e.toString());
        }
    }

    private String getDashboardForUser(User user) {
        if (user instanceof LandOwner)
            return "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml";
        if (user instanceof LandRegistrar)
            return "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml";
        if (user instanceof Surveyor)
            return "/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml";
        if (user instanceof Auditor)
            return "/group27/landRegistration/AllDashboards/AuditorDashboardView.fxml";
        if (user instanceof PublicUser)
            return "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml";
        if (user instanceof BankRepresentative)
            return "/group27/landRegistration/AllDashboards/BankRepresentativeDashboardView.fxml";

        return null;
    }
}
