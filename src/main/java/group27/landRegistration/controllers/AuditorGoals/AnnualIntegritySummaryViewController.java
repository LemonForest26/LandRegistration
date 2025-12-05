package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.time.Year;

public class AnnualIntegritySummaryViewController {

    @FXML private TextArea PreviewTA;
    @FXML private ComboBox<Integer> YearDP;

    private Auditor loggedInAuditor;

    @FXML
    private void initialize() {
        int currentYear = Year.now().getValue();
        YearDP.setItems(FXCollections.observableArrayList(
                currentYear, currentYear - 1, currentYear - 2, currentYear - 3, currentYear - 4
        ));
        YearDP.setValue(currentYear);

        PreviewTA.setEditable(false);
    }

    public void setUserData(User user) {
        if (user instanceof Auditor) {
            this.loggedInAuditor = (Auditor) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInAuditor;
    }

    @FXML
    public void CompileOA(ActionEvent actionEvent) {
        if (YearDP.getValue() == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Year",
                    "Please select a year to generate the report.");
            return;
        }

        int selectedYear = YearDP.getValue();

        try {
            String reportContent = loggedInAuditor.generateIntegrityReport(selectedYear);

            PreviewTA.setText(reportContent);

            loggedInAuditor.saveReportToDatabase(reportContent, "Annual Integrity Summary " + selectedYear);

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Report Compiled",
                    "The summary has been generated and saved to the Reports database.");

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Compilation Failed",
                    e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/AuditorDashboardView.fxml",
                actionEvent,
                (AuditorDashboardViewController controller) -> {
                    controller.setUserData(loggedInAuditor);
                }
        );
    }
}