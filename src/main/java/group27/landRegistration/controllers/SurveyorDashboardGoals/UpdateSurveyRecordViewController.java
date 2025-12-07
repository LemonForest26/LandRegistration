package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.controllers.AllDashboards.SurveyorDashboardViewController;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.nonUsers.SystemActivityLog;
import group27.landRegistration.users.Surveyor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class UpdateSurveyRecordViewController {

    @FXML
    private TextArea NotesTextArea;
    @FXML
    private TextField SurveyIDTextField;

    private Surveyor loggedInSurveyor;

    // Use specific type 'Surveyor' to access the specific methods like 'updateSurveyRecord'
    public void setUserData(User user) {
        if (user instanceof Surveyor) {
            this.loggedInSurveyor = (Surveyor) user;
        }
    }

    public User getLoggedInUser() {
        return loggedInSurveyor;
    }

    @FXML
    public void UpdateRecordOA(ActionEvent actionEvent) {
        // 1. Validation
        if (SurveyIDTextField.getText().isEmpty() || NotesTextArea.getText().isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please enter the Survey ID (Plot ID) and Notes.");
            return;
        }

        if (loggedInSurveyor == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Session Error", "Invalid User",
                    "You are not logged in as a Surveyor.");
            return;
        }

        try {
            int plotID = Integer.parseInt(SurveyIDTextField.getText());
            String notes = NotesTextArea.getText();

            // 2. Logic to update the record directly (Self-contained)
            FileManager<Plot> fm = new FileManager<>("Plot.dat");
            List<Plot> plots = fm.loadList();
            boolean found = false;

            for (int i = 0; i < plots.size(); i++) {
                Plot p = plots.get(i);
                if (p.getPlotID() == plotID) {
                    // Add Log Entry
                    String logEntry = String.format("Surveyor %s (ID: %d) added note: %s",
                            loggedInSurveyor.getName(), loggedInSurveyor.getUserID(), notes);
                    p.addSurveyLog(logEntry);

                    plots.set(i, p);
                    found = true;
                    break;
                }
            }

            // 3. Feedback and Logging
            if (found) {
                fm.saveList(plots);

                // Log System Activity
                SystemActivityLog sysLog = new SystemActivityLog(
                        0,
                        loggedInSurveyor.getUserID(),
                        "Updated Survey Record for Plot " + plotID
                );
                FileManager<SystemActivityLog> logFM = new FileManager<>("SystemActivity.dat");
                logFM.appendItem(sysLog);

                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Record Updated",
                        "The survey record has been updated successfully.");
                NotesTextArea.clear();
                SurveyIDTextField.clear();
            } else {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Not Found",
                        "No plot found for ID: " + plotID);
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Survey ID must be a numeric value.");
        }
    }

    @FXML
    public void BackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml",
                    actionEvent,
                    (SurveyorDashboardViewController controller) -> {
                        controller.setUserData(loggedInSurveyor);
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}