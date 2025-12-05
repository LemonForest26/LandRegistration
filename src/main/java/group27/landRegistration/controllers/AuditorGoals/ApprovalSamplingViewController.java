package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApprovalSamplingViewController {

    @FXML private ComboBox<User> RegistrarNameCB;
    @FXML private TableView<Application> ApprovalSamplingTV;
    @FXML private TableColumn<Application, Number> ApplicationIDTC;
    @FXML private TableColumn<Application, Number> PlotIDTC;
    @FXML private TableColumn<Application, String> StatusTC;
    @FXML private TableColumn<Application, String> ApprovedDateTC;

    @FXML private DatePicker StartDateDP;
    @FXML private DatePicker EndDateDP;

    private Auditor loggedInAuditor;
    private ObservableList<Application> masterList = FXCollections.observableArrayList();
    private Map<Integer, String> userNameCache = new HashMap<>();
    @FXML
    private TableColumn RegistrarNameTC;

    @FXML
    private void initialize() {
        // 1. Setup Columns
        ApplicationIDTC.setCellValueFactory(new PropertyValueFactory<>("applicationID"));
        PlotIDTC.setCellValueFactory(new PropertyValueFactory<>("plotID"));
        StatusTC.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Format Date
        ApprovedDateTC.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateUpdated().toString()));


        // 2. Load Helpers
        loadUserCache();
        loadRegistrars();
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

    private void loadUserCache() {
        FileManager<User> fm = new FileManager<>("users.dat");
        List<User> users = fm.loadList();
        for (User u : users) {
            userNameCache.put(u.getUserID(), u.getName());
        }
    }

    private void loadRegistrars() {
        FileManager<User> fm = new FileManager<>("users.dat");
        List<User> users = fm.loadList();
        List<User> registrars = users.stream()
                .filter(u -> u instanceof LandRegistrar)
                .collect(Collectors.toList());

        RegistrarNameCB.setItems(FXCollections.observableArrayList(registrars));

        // Display Name in ComboBox
        RegistrarNameCB.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User u) { return u == null ? "" : u.getName(); }
            @Override
            public User fromString(String s) { return null; }
        });
    }

    private void loadData() {
        if (loggedInAuditor == null) return;
        List<Application> approvedApps = loggedInAuditor.getApprovedApplications();
        masterList.setAll(approvedApps);
        ApprovalSamplingTV.setItems(masterList);
    }

    @FXML
    public void FilterRegistrarOA(ActionEvent actionEvent) {

        User selectedRegistrar = RegistrarNameCB.getValue();
        if (selectedRegistrar == null) {
            ApprovalSamplingTV.setItems(masterList);
            return;
        }

        String regName = selectedRegistrar.getName();

        List<Application> filtered = masterList.stream()
                .filter(app -> app.getNotes() != null && app.getNotes().contains(regName))
                .collect(Collectors.toList());

        ApprovalSamplingTV.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    public void DateFilterOA(ActionEvent actionEvent) {
        LocalDate start = StartDateDP.getValue();
        LocalDate end = EndDateDP.getValue();

        if (start == null && end == null) {
            ApprovalSamplingTV.setItems(masterList);
            return;
        }

        List<Application> filtered = masterList.stream()
                .filter(app -> {
                    LocalDate appDate = app.getDateUpdated();
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
                    "Select an application to flag.");
            return;
        }

        // --- FIXED METHOD CALL HERE ---
        loggedInAuditor.flagApplicationForReview(selectedApp, "Flagged by Auditor during sampling.");

        CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Application Flagged",
                "A flag note has been added to Application #" + selectedApp.getApplicationID());

        loadData(); // Refresh table
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