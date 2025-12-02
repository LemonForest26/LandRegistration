package group27.landRegistration.controllers;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
    private ComboBox UserCB;
    @javafx.fxml.FXML
    private TextField PasswordTF;

    public void initialize() {
        UserCB.getItems().addAll("Auditor", "Bank Representative", "Land Owner", "Land Registrar", "Public User", "Surveyor");
    }

    @javafx.fxml.FXML
    public void SignUpOA(ActionEvent actionEvent) {
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
