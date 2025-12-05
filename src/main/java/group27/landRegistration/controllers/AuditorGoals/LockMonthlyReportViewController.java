package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.nonUsers.MonthlyReport;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LockMonthlyReportViewController {

    @FXML private TableView<MonthlyReport> MonthlyReportTV;
    @FXML private TableColumn<MonthlyReport, String> MonthTC;
    @FXML private TableColumn<MonthlyReport, Integer> RegistrarTC;
    @FXML private TableColumn<MonthlyReport, String> StatusTC;
    @FXML private TableColumn<MonthlyReport, LocalDate> CreatedDateTC;
    @FXML private TableColumn<MonthlyReport, LocalDate> LastUpdatedTC;
    @FXML private ComboBox<String> SelectMonthCB;

    private Auditor loggedInAuditor;
    private ObservableList<MonthlyReport> masterList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        try {
            Class.forName("group27.landRegistration.nonUsers.MonthlyReport");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MonthTC.setCellValueFactory(new PropertyValueFactory<>("month"));
        RegistrarTC.setCellValueFactory(new PropertyValueFactory<>("registrarID"));
        StatusTC.setCellValueFactory(new PropertyValueFactory<>("status"));
        CreatedDateTC.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        LastUpdatedTC.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

        SelectMonthCB.setItems(FXCollections.observableArrayList(
                "All", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));
        SelectMonthCB.setValue("All");

        // Add Listener to Filter
        SelectMonthCB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            filterTable(newVal);
        });
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
        List<MonthlyReport> reports = loggedInAuditor.getAllMonthlyReports();
        masterList.setAll(reports);
        MonthlyReportTV.setItems(masterList);
    }

    private void filterTable(String month) {
        if (month == null || month.equals("All")) {
            MonthlyReportTV.setItems(masterList);
        } else {
            List<MonthlyReport> filtered = masterList.stream()
                    .filter(r -> r.getMonth().equalsIgnoreCase(month))
                    .collect(Collectors.toList());
            MonthlyReportTV.setItems(FXCollections.observableArrayList(filtered));
        }
    }

    @FXML
    public void ApproveLockOA(ActionEvent actionEvent) {
        MonthlyReport selectedReport = MonthlyReportTV.getSelectionModel().getSelectedItem();

        if (selectedReport == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Warning",
                    "Please select a report to lock.");
            return;
        }

        if ("Locked".equalsIgnoreCase(selectedReport.getStatus())) {
            CustomAlert.show(Alert.AlertType.INFORMATION, "Already Locked", "Info",
                    "This report is already locked and finalized.");
            return;
        }

        try {
            // Delegate to Model
            loggedInAuditor.lockMonthlyReport(selectedReport);

            // Refresh Table UI
            MonthlyReportTV.refresh(); // Updates the status cell visually

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Report Locked",
                    "The monthly report has been finalized and locked.");

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