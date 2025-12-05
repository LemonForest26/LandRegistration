package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class GenerateAuditReportViewController {

    @FXML private DatePicker StartDateDP;
    @FXML private DatePicker EndDateDP;

    private Auditor loggedInAuditor;

    public void setUserData(User user) {
        if (user instanceof Auditor) {
            this.loggedInAuditor = (Auditor) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInAuditor;
    }

    @FXML
    public void GenerateReportOA(ActionEvent actionEvent) {
        LocalDate start = StartDateDP.getValue();
        LocalDate end = EndDateDP.getValue();

        // 1. Validation
        if (start == null || end == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Dates",
                    "Please select both a Start Date and an End Date.");
            return;
        }

        if (end.isBefore(start)) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Range",
                    "End Date cannot be before Start Date.");
            return;
        }

        // 2. Delegate to Model to generate content
        String reportContent = loggedInAuditor.generateCustomAuditReport(start, end);

        // 3. Save File Dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Audit Report");
        fileChooser.setInitialFileName("AuditReport_" + start + "_to_" + end + ".txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        File destFile = fileChooser.showSaveDialog(stage);

        if (destFile != null) {
            try (FileWriter writer = new FileWriter(destFile)) {
                writer.write(reportContent);

                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Report Generated",
                        "The audit report has been saved successfully to:\n" + destFile.getAbsolutePath());

            } catch (IOException e) {
                CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Save Failed",
                        "Could not write to file: " + e.getMessage());
            }
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