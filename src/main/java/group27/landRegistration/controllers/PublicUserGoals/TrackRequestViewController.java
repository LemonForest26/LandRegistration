package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import group27.landRegistration.nonUsers.Feedback;
import group27.landRegistration.users.PublicUser;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class TrackRequestViewController {

    @FXML private TextField ReferenceIDTF;
    @FXML private Label StatusL;

    private PublicUser loggedInPublicUser;

    public void setUserData(User user) {
        if (user instanceof PublicUser) {
            this.loggedInPublicUser = (PublicUser) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInPublicUser;
    }

    @FXML
    public void TrackRequestOA(ActionEvent actionEvent) {
        // 1. Validation
        String idText = ReferenceIDTF.getText().trim();

        if (idText.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Field",
                    "Please enter your Request Reference ID.");
            return;
        }

        try {
            int requestID = Integer.parseInt(idText);

            // 2. Delegate to Model
            Feedback result = loggedInPublicUser.trackRequest(requestID);

            // 3. Update UI
            if (result != null) {
                // Found
                // Since 'Feedback' class doesn't have a mutable 'status' field (like 'Resolved'),
                // we treat existence as 'Received/Pending'.
                StatusL.setText("✔ REQUEST FOUND\n" +
                        "Type: " + result.getSubject() + "\n" +
                        "Date: " + result.getSubmissionDate() + "\n" +
                        "Current Status: Received / In Queue");
                StatusL.setTextFill(Color.GREEN);
            } else {
                // Not Found
                StatusL.setText("✘ NOT FOUND\n" +
                        "No request found with ID: " + requestID + "\n" +
                        "Please check the number.");
                StatusL.setTextFill(Color.RED);
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Reference ID must be a number.");
            StatusL.setText("");
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