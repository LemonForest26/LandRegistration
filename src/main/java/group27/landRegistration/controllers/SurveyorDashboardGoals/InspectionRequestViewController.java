package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.nonUsers.SystemActivityLog;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.stream.Collectors;

public class InspectionRequestViewController {

    @FXML
    private TableView<Application> InspectionTableView;
    @FXML
    private TableColumn<Application, String> PendingInspectionTableColumn;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    private void initialize() {
        // 1. Configure Table Column to show a summary string
        PendingInspectionTableColumn.setCellValueFactory(cellData -> {
            Application app = cellData.getValue();
            return new SimpleStringProperty(
                    "App ID: " + app.getApplicationID() +
                            " | Plot ID: " + app.getPlotID() +
                            " | Type: " + app.getNotes() // Assuming notes contain "New registration request" etc.
            );
        });

        // 2. Load Data
        loadInspectionRequests();
    }

    private void loadInspectionRequests() {
        FileManager<Application> fm = new FileManager<>("Application.dat");
        List<Application> allApps = fm.loadList();

        // Filter for applications that need inspection (e.g., status is "Pending")
        List<Application> pendingInspections = allApps.stream()
                .filter(app -> "Pending".equalsIgnoreCase(app.getStatus()))
                .collect(Collectors.toList());

        ObservableList<Application> observableList = FXCollections.observableArrayList(pendingInspections);
        InspectionTableView.setItems(observableList);
    }

    @FXML
    public void SubmitOnAction(ActionEvent actionEvent) {
        // 1. Get Selected Item
        Application selectedApp = InspectionTableView.getSelectionModel().getSelectedItem();

        if (selectedApp == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Select Request", "Please select an inspection request from the table to process.");
            return;
        }

        // 2. Update Application Status
        selectedApp.setStatus("Inspection Completed");
        selectedApp.addNote("Site inspection completed by Surveyor ID: " + loggedInUser.getUserID());

        // 3. Save Changes to File
        FileManager<Application> fm = new FileManager<>("Application.dat");
        List<Application> allApps = fm.loadList();

        for (int i = 0; i < allApps.size(); i++) {
            if (allApps.get(i).getApplicationID() == selectedApp.getApplicationID()) {
                allApps.set(i, selectedApp);
                break;
            }
        }
        fm.saveList(allApps);

        // 4. Log Activity
        FileManager<SystemActivityLog> logFM = new FileManager<>("SystemActivity.dat");
        SystemActivityLog log = new SystemActivityLog(0, loggedInUser.getUserID(), "Completed Inspection for App ID " + selectedApp.getApplicationID());
        logFM.appendItem(log);

        // 5. Success Message & Refresh
        CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Inspection Recorded", "The application status has been updated to 'Inspection Completed'.");
        loadInspectionRequests(); // Refresh table to remove the processed item
    }

    @FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml",
                    actionEvent,
                    controller -> {
                        try {
                            controller.getClass()
                                    .getMethod("setUserData", User.class)
                                    .invoke(controller, loggedInUser);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}