package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ApprovalSamplingViewController {

    @FXML private TableView<Application> ApprovalSamplingTV;
    @FXML private TableColumn<Application, Number> ApplicationIDTC;
    @FXML private TableColumn<Application, Number> PlotIDTC;
    @FXML private TableColumn<Application, String> ApprovedDateTC;
    @FXML private TableColumn<Application, String> StatusTC;

    @FXML private DatePicker StartDateDP;
    @FXML private DatePicker EndDateDP;

    private Auditor loggedInAuditor;
    private ObservableList<Application> masterList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // 1. Setup Columns (Matching FXML fx:ids)
        ApplicationIDTC.setCellValueFactory(new PropertyValueFactory<>("applicationID"));
        PlotIDTC.setCellValueFactory(new PropertyValueFactory<>("plotID"));
        StatusTC.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Format Date Column
        ApprovedDateTC.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateUpdated() != null
                        ? cellData.getValue().getDateUpdated().toString()
                        : "N/A"));
    }

    public void setUserData(User user) {
        if (user instanceof Auditor) {
            this.loggedInAuditor = (Auditor) user;
            loadData();
        }
    }

    public User getLoggedInUser(){
        return loggedInAuditor;
    }

    private void loadData() {
        if (loggedInAuditor == null) return;
        List<Application> approvedApps = loggedInAuditor.getApprovedApplications();
        masterList.setAll(approvedApps);
        ApprovalSamplingTV.setItems(masterList);
    }

    @FXML
    public void DateFilterOA(ActionEvent actionEvent) {
        LocalDate start = StartDateDP.getValue();
        LocalDate end = EndDateDP.getValue();

        // If fields are cleared, reset table
        if (start == null && end == null) {
            ApprovalSamplingTV.setItems(masterList);
            return;
        }

        // Filter Logic
        List<Application> filtered = masterList.stream()
                .filter(app -> {
                    LocalDate appDate = app.getDateUpdated();
                    if (appDate == null) return false;

                    boolean afterStart = (start == null) || !appDate.isBefore(start);
                    boolean beforeEnd = (end == null) || !appDate.isAfter(end);

                    return afterStart && beforeEnd;
                })
                .collect(Collectors.toList());

        ApprovalSamplingTV.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    public void FlagSampleOA(ActionEvent actionEvent) {
        Application selectedApp = ApprovalSamplingTV.getSelectionModel().getSelectedItem();

        if (selectedApp == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Warning",
                    "Please select an application from the table to flag.");
            return;
        }

        // Input Dialog for Flag Reason
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Flag Application");
        dialog.setHeaderText("Flagging Application #" + selectedApp.getApplicationID());
        dialog.setContentText("Enter reason for flagging:");

        dialog.showAndWait().ifPresent(reason -> {
            if (reason.trim().isEmpty()) return;

            // Delegate to Model
            loggedInAuditor.flagApplicationForReview(selectedApp, reason);

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Flag Added",
                    "The application has been marked for review.");

            // Refresh Data
            loadData();
        });
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