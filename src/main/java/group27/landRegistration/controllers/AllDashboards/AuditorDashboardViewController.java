package group27.landRegistration.controllers.AllDashboards;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class AuditorDashboardViewController {
    @javafx.fxml.FXML
    private Label UserNameLb;
    @javafx.fxml.FXML
    private Label UserIDLb;

    public void setUserData(User user) {
        UserNameLb.setText(user.getName());
        UserIDLb.setText(String.valueOf(user.getUserID()));
    }

    @javafx.fxml.FXML
    public void ValuationCheckOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/ValuationCheckView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void UserPermissionOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/ReviewUserPermissionsView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void MonthlyReportLockOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/LockMonthlyReportView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void ApprovalSamplingOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/ApprovalSamplingView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void ActivityLogOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/ActivityLogView.fxml", actionEvent);
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
    public void IntegritySummaryOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AnnualIntegritySummaryView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void GenerateAuditReportOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/GenerateAuditReportView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML


    public void EditProfileOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/EditProfileView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
