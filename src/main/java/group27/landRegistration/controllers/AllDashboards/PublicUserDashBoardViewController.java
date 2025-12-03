package group27.landRegistration.controllers.AllDashboards;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class PublicUserDashBoardViewController {

    @javafx.fxml.FXML
    private Label UserNameLb;
    @javafx.fxml.FXML
    private Label UserIDLb;

    public void setUserData(User user) {
        UserNameLb.setText(user.getName());
        UserIDLb.setText(String.valueOf(user.getUserID()));
    }

    @javafx.fxml.FXML
    public void NoticesOA(ActionEvent actionEvent) {
    }

    @Deprecated
    public void SearchPlotIDOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void FormsAndDownloadsOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void SubmitRequestOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void TrackRequestOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void VerifyCertificateOA(ActionEvent actionEvent) {
    }

    @Deprecated
    public void PublicMapsOA(ActionEvent actionEvent) {
    }

    @Deprecated
    public void SearchByAreaOA(ActionEvent actionEvent) {
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
    public void SearchPlotOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BasicFeeChartOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void EditProfileOA(ActionEvent actionEvent) {
    }
}
