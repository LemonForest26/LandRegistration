package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.controllers.AllDashboards.LandOwnerDashBoardViewController;
import group27.landRegistration.users.LandOwner;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SubmitFeedbackViewController {

    @FXML private TextField SubjectTF;
    @FXML private TextArea MessageTA;

    private LandOwner loggedInLandOwner;

    // Cast immediately to allow access to LandOwner specific methods
    public void setUserData(User user) {
        if (user instanceof LandOwner) {
            this.loggedInLandOwner = (LandOwner) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInLandOwner;
    }

    @FXML
    public void SubmitFeedbackOA(ActionEvent actionEvent) {
        // 1. Validation
        String subject = SubjectTF.getText().trim();
        String message = MessageTA.getText().trim();

        if (subject.isEmpty() || message.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please provide both a subject and a message.");
            return;
        }

        try {
            // 2. Delegate to Model
            loggedInLandOwner.sendFeedback(subject, message);

            // 3. Success Feedback
            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Feedback Sent",
                    "Thank you! Your feedback has been recorded successfully.");

            // Clear fields
            SubjectTF.clear();
            MessageTA.clear();

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Save Failed",
                    "Could not save feedback: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Type-safe navigation (Fixes the reflection error from the snippet)
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(loggedInLandOwner);
                }
        );
    }
}