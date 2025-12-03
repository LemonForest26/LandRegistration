package group27.landRegistration.controllers;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
        String userType = (String) UserTypeCB.getValue();
        String userID = UserIDTF.getText().trim();
        String password = PasswordTF.getText().trim();

        // Input validation
        if (userType == null || userType.isEmpty()) {
            System.out.println("Select a user type!");
            return;
        }

        if (userID.isEmpty()) {
            System.out.println("User ID cannot be empty!");
            return;
        }

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty!");
            return;
        }

        // If all good, search user in file


    }
}
