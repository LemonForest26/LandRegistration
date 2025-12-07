package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.controllers.AllDashboards.SurveyorDashboardViewController;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StartNewSurveyViewController {

    @FXML private DatePicker SurveyDatePicker;
    // NOTE: This variable name must match the fx:id in your FXML file exactly.
    // It seems your FXML uses "BounaryCoordinatesTextArea" (missing 'd').
    @FXML private TextArea BounaryCoordinatesTextArea;
    @FXML private TextField OwnerIDTextField;

    private User loggedInUser;

    /**
     * Sets the logged-in user for this session.
     * @param user The user passed from the previous dashboard.
     */
    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    @FXML
    public void SubmitOnAction(ActionEvent actionEvent) {
        // 0. Pre-check: Ensure user is logged in
        if (loggedInUser == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Session Error", "No User Logged In",
                    "Please return to the login screen and try again.");
            return;
        }

        // 1. Validate date
        LocalDate surveyDate = SurveyDatePicker.getValue();
        if (surveyDate == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Date",
                    "Please select a survey date.");
            return;
        }

        // 2. Validate owner ID input
        String ownerIDText = OwnerIDTextField.getText().trim();
        if (ownerIDText.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Owner ID",
                    "Please enter the ID of the plot owner.");
            return;
        }

        int ownerID;
        try {
            ownerID = Integer.parseInt(ownerIDText);
        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Owner ID",
                    "Owner ID must be a numeric value.");
            return;
        }

        // 3. Validate boundary coordinates
        // Using the variable name that matches the FXML fx:id
        if (BounaryCoordinatesTextArea == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Configuration Error",
                    "The text area BounaryCoordinatesTextArea was not injected properly. Check FXML fx:id.");
            return;
        }

        String coordinates = BounaryCoordinatesTextArea.getText().trim();
        if (coordinates.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Coordinates",
                    "Please enter the boundary coordinates.");
            return;
        }

        try {
            // 4. Load existing plots first to generate the next ID
            FileManager<Plot> plotFM = new FileManager<>("Plot.dat");
            List<Plot> plots = plotFM.loadList();

            // Initialize list if file was empty or didn't exist
            if (plots == null) {
                plots = new ArrayList<>();
            }

            // Generate new Plot ID
            int newId = 1;
            if (!plots.isEmpty()) {
                // Get the last ID and increment to ensure uniqueness
                newId = plots.get(plots.size() - 1).getPlotID() + 1;
            }

            // 5. Create new Plot object
            // Using placeholder values for fields not yet determined by this form
            Plot newPlot = new Plot(
                    ownerID,
                    "Location Pending",
                    "Unzoned",
                    0.0
            );
            newPlot.setPlotID(newId); // Explicitly set the generated ID

            // 6. Add survey record text
            String logEntry = "Survey Date: " + surveyDate +
                    "\nSurveyor ID: " + loggedInUser.getUserID() +
                    "\nCoordinates:\n" + coordinates + "\n";

            newPlot.addSurveyLog(logEntry);

            // 7. Save the plot to file
            plots.add(newPlot);
            plotFM.saveList(plots);

            // 8. Success message
            CustomAlert.show(Alert.AlertType.INFORMATION,
                    "Survey Started",
                    "Plot Created Successfully",
                    "New Plot ID generated: " + newPlot.getPlotID());

            // Clear fields after success
            SurveyDatePicker.setValue(null);
            OwnerIDTextField.clear();
            BounaryCoordinatesTextArea.clear();

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Save Failed",
                    "Could not save the new survey: " + e.getMessage());
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
                    controller.setUserData(loggedInUser);
                }
        );
    }
}