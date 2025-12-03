package group27.landRegistration.controllers.AllDashboards;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;

public class LandOwnerDashBoardViewController {
    @javafx.fxml.FXML
    public void NewRegistrationRequestOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void MutationRequestOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void CertificatesOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void MyLandRecordsOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void PayTaxesOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void TrackApplicationOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void LogOutOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LogInView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void FeedbackOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void EditProfileOA(ActionEvent actionEvent) {
    }
}
