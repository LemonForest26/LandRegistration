package group27.landRegistration.controllers;

import group27.landRegistration.utility.PageLoader;
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
            PageLoader page = new PageLoader();
            page.load("/group27/landRegistration/SignUpView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void ForgetPasswordOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void SignInOA(ActionEvent actionEvent) {
    }
}
