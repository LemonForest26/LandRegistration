package group27.landRegistration.controllers.AllDashboards;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class LandOwnerDashBoardViewController {
    @javafx.fxml.FXML
    private Label UserNameLb;
    @javafx.fxml.FXML
    private Label UserIDLb;

    public void setUserData(User user) {
        UserNameLb.setText(user.getName());
        UserIDLb.setText(String.valueOf(user.getUserID()));
    }

    @javafx.fxml.FXML
    public void NewRegistrationRequestOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LandOwnerGoals/SubmitNewRegistrationRequestView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void MutationRequestOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LandOwnerGoals/RequestCorrectionMutationView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void CertificatesOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LandOwnerGoals/DownloadCertificateView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void MyLandRecordsOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LandOwnerGoals/ViewMyLandRecordsView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void PayTaxesOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LandOwnerGoals/PayTaxView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void TrackApplicationOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LandOwnerGoals/TrackApplicationView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LandOwnerGoals/SubmitFeedbackView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void EditProfileOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LandOwnerGoals/EditProfileView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
