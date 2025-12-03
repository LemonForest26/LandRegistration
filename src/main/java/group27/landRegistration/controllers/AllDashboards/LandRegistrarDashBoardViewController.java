package group27.landRegistration.controllers.AllDashboards;

import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class LandRegistrarDashBoardViewController {
    @javafx.fxml.FXML
    private Label UserNameLb;
    @javafx.fxml.FXML
    private Label UserIDLb;

    // <-- Use User, not LandRegistrar
    public void setUserData(User user) {
        UserNameLb.setText(user.getName());
        UserIDLb.setText(String.valueOf(user.getUserID()));
    }



    @javafx.fxml.FXML
    public void OfficerPerformanceOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void GenerateCertificateOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void ZoningUpdateOA(ActionEvent actionEvent) {
    }

    @Deprecated
    public void DailySummaryReportOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void ApproveRejectOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void ReviewPendingApplicationsOA(ActionEvent actionEvent) {
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
    public void DisputesOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void TransferOwnershipOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void EditProfileOA(ActionEvent actionEvent) {
    }
}
