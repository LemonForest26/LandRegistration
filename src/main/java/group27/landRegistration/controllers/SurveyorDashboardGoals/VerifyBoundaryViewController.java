package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class VerifyBoundaryViewController {
    @javafx.fxml.FXML
    private TextField MeasurementTextField;
    @javafx.fxml.FXML
    private TextField PlotIDTextField;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    public void SubmitOnAction(ActionEvent actionEvent) {
        String plotID = PlotIDTextField.getText();
        String measurementStr = MeasurementTextField.getText();

        // 1. Validation
        if (plotID.isEmpty() || measurementStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill in both Plot ID and Measurement fields.");
            return;
        }

        try {
            double measurement = Double.parseDouble(measurementStr);

            // 2. Process Data (Save to file)
            boolean success = saveVerificationDetails(plotID, measurement);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Boundary verification submitted successfully.");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "System Error", "Could not save verification details.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Measurement must be a valid numeric value.");
        }
    }

    private boolean saveVerificationDetails(String plotID, double measurement) {
        // Appending to a text file for persistence
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("BoundaryVerifications.txt", true))) {
            String surveyorId = (loggedInUser != null) ? loggedInUser.toString() : "Unknown"; // Adjust based on User class methods like getId()
            String record = String.format("Date: %s | Surveyor: %s | Plot ID: %s | Measurement: %.2f sq meters",
                    LocalDate.now(), surveyorId, plotID, measurement);

            writer.write(record);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        PlotIDTextField.clear();
        MeasurementTextField.clear();
    }

    @javafx.fxml.FXML
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