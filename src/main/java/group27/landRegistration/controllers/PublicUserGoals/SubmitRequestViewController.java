package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import group27.landRegistration.users.PublicUser;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SubmitRequestViewController {

    @FXML private TextArea MessageTA;
    @FXML private TextField NameTF;
    @FXML private ComboBox<String> RequestTypeCB;

    private PublicUser loggedInPublicUser;

    @FXML
    private void initialize() {
        // Populate Request Types
        RequestTypeCB.setItems(FXCollections.observableArrayList(
                "General Inquiry",
                "Technical Issue",
                "Appointment Request",
                "Complaint",
                "Other"
        ));
    }

    public void setUserData(User user) {
        if (user instanceof PublicUser) {
            this.loggedInPublicUser = (PublicUser) user;


            if (loggedInPublicUser.getName() != null) {
                NameTF.setText(loggedInPublicUser.getName());
                NameTF.setEditable(false);
            }
        }
    }

    public User getLoggedInUser(){
        return loggedInPublicUser;
    }

    @FXML
    public void SubmitRequestOA(ActionEvent actionEvent) {
        // 1. Validation
        String type = RequestTypeCB.getValue();
        String message = MessageTA.getText().trim();
        // Name is visual only, we use ID for tracking, but checking it ensures form completeness
        String name = NameTF.getText().trim();

        if (type == null || message.isEmpty() || name.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please fill in all fields and select a request type.");
            return;
        }

        try {
            // 2. Delegate to Model
            loggedInPublicUser.submitGeneralRequest(type, message);

            // 3. Success Feedback
            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Request Submitted",
                    "Your request has been recorded. Reference ID tracked by your User ID.");

            // Clear fields
            MessageTA.clear();
            RequestTypeCB.getSelectionModel().clearSelection();

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Submission Failed",
                    "Could not save request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Clean navigation
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml",
                actionEvent,
                (PublicUserDashBoardViewController controller) -> {
                    controller.setUserData(loggedInPublicUser);
                }
        );
    }
}