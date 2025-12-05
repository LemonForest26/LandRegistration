package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.nonUsers.SystemActivityLog;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ActivityLogViewController {

    @FXML private TableView<SystemActivityLog> SystemActivityTV;
    @FXML private TableColumn<SystemActivityLog, String> ActionTC;
    @FXML private TableColumn<SystemActivityLog, String> UserTC;
    @FXML private TableColumn<SystemActivityLog, String> TimestampTC;

    private Auditor loggedInAuditor;

    @FXML
    private void initialize() {
        // Setup Columns
        TimestampTC.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFormattedTimestamp()));

        UserTC.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUserID())));

        // If flagged, show visual indicator in text
        ActionTC.setCellValueFactory(cellData -> {
            SystemActivityLog log = cellData.getValue();
            String prefix = log.isFlagged() ? "🚩 " : "";
            return new SimpleStringProperty(prefix + log.getAction());
        });
    }

    public void setUserData(User user) {
        if (user instanceof Auditor) {
            this.loggedInAuditor = (Auditor) user;
            loadData();
        }
    }

    private void loadData() {
        if (loggedInAuditor != null) {
            SystemActivityTV.setItems(FXCollections.observableArrayList(
                    loggedInAuditor.viewSystemActivity()
            ));
        }
    }

    public User getLoggedInUser(){
        return loggedInAuditor;
    }

    @FXML
    public void FlagEntryOA(ActionEvent actionEvent) {
        SystemActivityLog selectedLog = SystemActivityTV.getSelectionModel().getSelectedItem();

        if (selectedLog == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Warning",
                    "Please select an activity log to flag.");
            return;
        }

        if (selectedLog.isFlagged()) {
            CustomAlert.show(Alert.AlertType.INFORMATION, "Already Flagged", "Info",
                    "This entry has already been flagged.");
            return;
        }

        try {
            // Delegate to Model
            loggedInAuditor.flagSystemActivity(selectedLog);

            // Refresh Table
            loadData();

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Entry Flagged",
                    "The suspicious activity has been flagged for review.");

        } catch (Exception e) {
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