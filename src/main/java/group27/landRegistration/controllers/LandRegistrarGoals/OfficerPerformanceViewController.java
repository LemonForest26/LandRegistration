package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
import group27.landRegistration.nonUsers.OfficerLog;
import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.Surveyor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OfficerPerformanceViewController {

    @FXML private ComboBox<String> OfficerTypeCB;
    @FXML private ComboBox<User> OfficerNameCB; // Stores User objects, displays Names
    @FXML private TableView<OfficerLog> PerformanceTV;
    @FXML private TableColumn<OfficerLog, LocalDate> DateTC;
    @FXML private TableColumn<OfficerLog, String> ActionsTC;

    private LandRegistrar loggedInRegistrar;

    @FXML
    private void initialize() {
        // 1. Setup Table Columns
        DateTC.setCellValueFactory(new PropertyValueFactory<>("date"));
        ActionsTC.setCellValueFactory(new PropertyValueFactory<>("actionDescription"));

        // 2. Setup Officer Type ComboBox
        OfficerTypeCB.setItems(FXCollections.observableArrayList("Surveyor", "Land Registrar")); // Add other types if needed

        // 3. Listener: When Type changes, populate Name ComboBox
        OfficerTypeCB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadOfficersByType(newVal);
            }
        });

        // 4. Listener: When Name changes, load Table Data
        OfficerNameCB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newUser) -> {
            if (newUser != null) {
                loadPerformanceLogs(newUser.getUserID());
            }
        });

        // 5. Setup Name ComboBox to show String names but store User objects
        OfficerNameCB.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user == null ? "" : user.getName() + " (ID: " + user.getUserID() + ")";
            }

            @Override
            public User fromString(String string) {
                return null; // Not needed for read-only combo box
            }
        });
    }

    public void setUserData(User user) {
        if (user instanceof LandRegistrar) {
            this.loggedInRegistrar = (LandRegistrar) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInRegistrar;
    }

    private void loadOfficersByType(String type) {
        FileManager<User> userFM = new FileManager<>("users.dat");
        List<User> allUsers = userFM.loadList();

        List<User> filteredUsers;

        if (type.equals("Surveyor")) {
            filteredUsers = allUsers.stream()
                    .filter(u -> u instanceof Surveyor)
                    .collect(Collectors.toList());
        } else if (type.equals("Land Registrar")) {
            filteredUsers = allUsers.stream()
                    .filter(u -> u instanceof LandRegistrar)
                    .collect(Collectors.toList());
        } else {
            filteredUsers = FXCollections.emptyObservableList();
        }

        OfficerNameCB.setItems(FXCollections.observableArrayList(filteredUsers));
    }

    private void loadPerformanceLogs(int officerID) {
        if (loggedInRegistrar == null) return;

        List<OfficerLog> logs = loggedInRegistrar.viewOfficerPerformance(officerID);
        PerformanceTV.setItems(FXCollections.observableArrayList(logs));
    }

    @FXML
    public void FlagOfficerOA(ActionEvent actionEvent) {
        User selectedOfficer = OfficerNameCB.getValue();

        if (selectedOfficer == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Selection Error", "No Officer Selected",
                    "Please select an officer to flag.");
            return;
        }

        // Ask for reason using a simple Dialog or just a default for now.
        // Since your FXML didn't have a text field for "Reason", I'll use a standard TextInputDialog or hardcode it.
        // For simplicity in this layout, we will assume a generic flag or prompt.

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Flag Officer");
        dialog.setHeaderText("Flagging " + selectedOfficer.getName());
        dialog.setContentText("Please enter the reason for flagging:");

        dialog.showAndWait().ifPresent(reason -> {
            if (reason.trim().isEmpty()) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Reason Empty", "Flagging cancelled.");
                return;
            }

            // Delegate to Model
            loggedInRegistrar.flagOfficer(selectedOfficer.getUserID(), reason);

            // Refresh Table
            loadPerformanceLogs(selectedOfficer.getUserID());

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Officer Flagged",
                    "The flag has been recorded in the system.");
        });
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
                actionEvent,
                (LandRegistrarDashBoardViewController controller) -> {
                    controller.setUserData(loggedInRegistrar);
                }
        );
    }
}