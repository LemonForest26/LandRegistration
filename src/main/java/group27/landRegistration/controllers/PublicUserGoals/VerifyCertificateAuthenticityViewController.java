package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VerifyCertificateAuthenticityViewController {
    @javafx.fxml.FXML
    private Label StatusL;
    @javafx.fxml.FXML
    private TextField CertificateIDTF;

    @javafx.fxml.FXML
    public void VerifyCertificateOA(ActionEvent actionEvent) {
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
