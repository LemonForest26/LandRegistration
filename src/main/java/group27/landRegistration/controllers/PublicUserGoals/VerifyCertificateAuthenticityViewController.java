package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import group27.landRegistration.nonUsers.Certificate;
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

public class VerifyCertificateAuthenticityViewController {

    @FXML private Label StatusL;
    @FXML private TextField CertificateIDTF;

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
    public void VerifyCertificateOA(ActionEvent actionEvent) {
        // 1. Validation
        String idText = CertificateIDTF.getText().trim();

        if (idText.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Field",
                    "Please enter a Certificate ID.");
            return;
        }

        try {
            int certID = Integer.parseInt(idText);

            // 2. Delegate to Model
            Certificate result = loggedInPublicUser.verifyCertificate(certID);

            // 3. Update UI based on result
            if (result != null) {
                // Success Case
                StatusL.setText("✔ VALID: Authentic Certificate found.\n" +
                        "Issued Date: " + result.getIssueDate() + "\n" +
                        "Associated App ID: " + result.getApplicationID());
                StatusL.setTextFill(Color.GREEN);
            } else {
                // Failure Case
                StatusL.setText("✘ INVALID: No certificate found with this ID.\n" +
                        "Please check the number and try again.");
                StatusL.setTextFill(Color.RED);
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Certificate ID must be numeric.");
            StatusL.setText(""); // Clear status on error
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