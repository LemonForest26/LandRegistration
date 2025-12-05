package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.nonUsers.UserPermission;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.util.List;

public class ReviewUserPermissionsViewController {

    @FXML private TableView<UserPermission> PermissionsTV;
    @FXML private TableColumn<UserPermission, String> ModuleTC;
    @FXML private TableColumn<UserPermission, String> ActionTC;
    @FXML private TableColumn<UserPermission, String> AccessLevelTC;
    @FXML private ComboBox<User> SystemUsersCB;

    private Auditor loggedInAuditor;

    @FXML
    private void initialize() {
        // 1. Setup Columns
        ModuleTC.setCellValueFactory(new PropertyValueFactory<>("module"));
        AccessLevelTC.setCellValueFactory(new PropertyValueFactory<>("accessLevel"));

        // Custom cell to show Flag status visually
        ActionTC.setCellValueFactory(cellData -> {
            UserPermission p = cellData.getValue();
            String prefix = p.isFlaggedForReview() ? "🚩 " : "";
            return new SimpleStringProperty(prefix + p.getAction());
        });

        // 2. Setup ComboBox Converter (to show Names instead of Object IDs)
        SystemUsersCB.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user == null ? "" : user.getName() + " (" + user.getClass().getSimpleName() + ")";
            }
            @Override
            public User fromString(String string) { return null; }
        });

        // 3. Add Listener to load table when user is selected
        SystemUsersCB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newUser) -> {
            if (newUser != null) {
                loadPermissions(newUser.getUserID());
            }
        });
    }

    public void setUserData(User user) {
        if (user instanceof Auditor) {
            this.loggedInAuditor = (Auditor) user;
            loadUserList();
        }
    }

    public User getLoggedInUser(){
        return loggedInAuditor;
    }

    private void loadUserList() {
        if (loggedInAuditor == null) return;
        List<User> users = loggedInAuditor.getAllSystemUsers();
        SystemUsersCB.setItems(FXCollections.observableArrayList(users));
    }

    private void loadPermissions(int userID) {
        List<UserPermission> perms = loggedInAuditor.getPermissionsForUser(userID);
        PermissionsTV.setItems(FXCollections.observableArrayList(perms));
    }

    @FXML
    public void MarkForReviewOA(ActionEvent actionEvent) {
        UserPermission selectedPerm = PermissionsTV.getSelectionModel().getSelectedItem();

        if (selectedPerm == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Warning",
                    "Please select a permission entry to flag.");
            return;
        }

        if (selectedPerm.isFlaggedForReview()) {
            CustomAlert.show(Alert.AlertType.INFORMATION, "Already Flagged", "Info",
                    "This permission is already marked for review.");
            return;
        }

        try {
            // Delegate to Model
            loggedInAuditor.markPermissionForReview(selectedPerm);

            // Refresh Table
            PermissionsTV.refresh();

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Flagged",
                    "Permission marked for security review.");

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