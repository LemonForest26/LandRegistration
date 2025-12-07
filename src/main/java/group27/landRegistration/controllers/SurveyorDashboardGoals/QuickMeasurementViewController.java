package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.controllers.AllDashboards.SurveyorDashboardViewController;
import group27.landRegistration.users.Surveyor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class QuickMeasurementViewController {

    @FXML private TextField plotiDTextfield;
    @FXML private TextField MeasurementtextField;

    private Surveyor loggedInSurveyor;

    /**
     * Sets the user data for this controller.
     * Validates that the user is a Surveyor instance.
     * @param user The logged-in user passed from the previous screen.
     */
    public void setUserData(User user) {
        if (user instanceof Surveyor) {
            this.loggedInSurveyor = (Surveyor) user;
        } else {
            // Handle cases where the user might not be a Surveyor (e.g. Admin view)
            // or log a warning.
            System.err.println("Error: User passed to QuickMeasurementViewController is not a Surveyor.");
        }
    }

    public User getLoggedInUser(){
        return loggedInSurveyor;
    }

    @FXML
    public void SaveMeasurementOnAction(ActionEvent actionEvent) {
        // 0. Pre-check: Ensure a surveyor is logged in
        if (loggedInSurveyor == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Authentication Error", "Invalid Session",
                    "No valid surveyor account is logged in.");
            return;
        }

        // 1. Validation: Check for empty fields
        if (plotiDTextfield.getText().isEmpty() || MeasurementtextField.getText().isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please enter both Plot ID and the measured area.");
            return;
        }

        try {
            int plotID = Integer.parseInt(plotiDTextfield.getText());
            double newArea = Double.parseDouble(MeasurementtextField.getText());

            // Validate logic ranges
            if (newArea <= 0) {
                CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Area",
                        "Area must be a positive number greater than 0.");
                return;
            }

            // 2. Delegate Logic to Model
            // specific method on Surveyor class to handle logic/database
            boolean success = loggedInSurveyor.recordMeasurement(plotID, newArea);

            // 3. Feedback
            if (success) {
                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Measurement Saved",
                        "The plot area has been updated successfully.");
                MeasurementtextField.clear();
                plotiDTextfield.clear();
            } else {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Operation Failed",
                        "Could not update measurement. Ensure Plot ID " + plotID + " exists and is assigned to you.");
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Plot ID must be an integer (e.g., 101) and Area must be a number (e.g., 150.5).");
        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Unexpected Error",
                    "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        // Navigate back to the Dashboard using lambda for type-safe data passing
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml",
                actionEvent,
                (SurveyorDashboardViewController controller) -> {
                    controller.setUserData(loggedInSurveyor);
                }
        );
    }
}