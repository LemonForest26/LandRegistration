package group27.landRegistration.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SignUpViewController {

    @javafx.fxml.FXML
    private TextField PasswordTextField;
    @javafx.fxml.FXML
    private TextField NIDTextField;
    @javafx.fxml.FXML
    private TextField UserNameTextField;
    @javafx.fxml.FXML
    private TextField PhoneNumberTextField;
    @javafx.fxml.FXML
    private DatePicker DOBDatePicker;
    @javafx.fxml.FXML
    private ComboBox UserComboBox;
    @javafx.fxml.FXML
    private TextField EmailTextField;

    @javafx.fxml.FXML
    public void SignUpOnAction(ActionEvent actionEvent) {
    }
}
