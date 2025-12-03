package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EditProfileViewController {
    @javafx.fxml.FXML
    private TextField AddressTF;
    @javafx.fxml.FXML
    private TextField PhoneTF;
    @javafx.fxml.FXML
    private DatePicker DoBDP;
    @javafx.fxml.FXML
    private TextField EmailTF;
    @javafx.fxml.FXML
    private TextField NameTF;
    @javafx.fxml.FXML
    private PasswordField NewPasswordTF;
    @javafx.fxml.FXML
    private PasswordField ConfirmPasswordTF;

    @javafx.fxml.FXML
    public void SaveProfileOA(ActionEvent actionEvent) {
    }

    @Deprecated
    public void HomeOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
