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

    // Cast User to Surveyor immediately for model access
    public void setUserData(User user) {
        if (user instanceof Surveyor) {
            this.loggedInSurveyor = (Surveyor) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInSurveyor;
    }

    @FXML
    public void SaveMeasurementOnAction(ActionEvent actionEvent) {
        // 1. Validation
        if (plotiDTextfield.getText().isEmpty() || MeasurementtextField.getText().isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please enter both Plot ID and the measured area.");
            return;
        }

        try {
            int plotID = Integer.parseInt(plotiDTextfield.getText());
            double newArea = Double.parseDouble(MeasurementtextField.getText());

            if (newArea <= 0) {
                CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Area",
                        "Area must be greater than 0.");
                return;
            }

            // 2. Delegate Logic to Model
            boolean success = loggedInSurveyor.recordMeasurement(plotID, newArea);

            // 3. Feedback
            if (success) {
                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Measurement Saved",
                        "The plot area has been updated successfully.");
                MeasurementtextField.clear();
                plotiDTextfield.clear();
            } else {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Plot Not Found",
                        "No plot found with ID: " + plotID);
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Plot ID must be an integer and Area must be a number.");
        }
    }

    @FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        // Clean navigation using Lambda
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml",
                actionEvent,
                (SurveyorDashboardViewController controller) -> {
                    controller.setUserData(loggedInSurveyor);
                }
        );
    }
}