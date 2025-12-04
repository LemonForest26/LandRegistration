package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;

public class StartNewSurveyViewController {

    @javafx.fxml.FXML
    private DatePicker SurveyDatePicker;
    @javafx.fxml.FXML
    private TextArea BounaryCoordinatesTextArea;

    private User loggedInUser;
    @javafx.fxml.FXML
    private TextField OwnerIDTextField;

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    @javafx.fxml.FXML
    public void SubmitOnAction(ActionEvent actionEvent) {

        // 1. Validate date
        LocalDate surveyDate = SurveyDatePicker.getValue();
        if (surveyDate == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Missing Date",
                    "Survey date is required", "Please select a date.");
            return;
        }

        // 2. Validate owner ID input
        String ownerIDText = OwnerIDTextField.getText().trim();
        if (ownerIDText.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Missing Owner ID",
                    "Owner ID Required", "Enter the owner ID.");
            return;
        }

        int ownerID;
        try {
            ownerID = Integer.parseInt(ownerIDText);
        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Invalid Input",
                    "Owner ID must be numeric", "Correct the owner ID.");
            return;
        }

        // 3. Validate boundary coordinates
        String coordinates = BounaryCoordinatesTextArea.getText().trim();
        if (coordinates.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Missing Coordinates",
                    "Boundary coordinates required",
                    "Enter boundary coordinate list.");
            return;
        }

        // 4. Create new Plot object
        // PLACEHOLDER location/zoning/area → replace if needed later
        Plot newPlot = new Plot(
                ownerID,
                "Location Pending",
                "Unzoned",
                0.0
        );

        // 5. Add survey record text
        String logEntry = "Survey Date: " + surveyDate +
                "\nSurveyor ID: " + loggedInUser.getUserID() +
                "\nCoordinates:\n" + coordinates + "\n";

        newPlot.addSurveyLog(logEntry);

        // 6. Save the plot to file
        FileManager<Plot> plotFM = new FileManager<>("Plot.dat");

        List<Plot> plots = plotFM.loadList();
        plots.add(newPlot);
        plotFM.saveList(plots);

        // 7. Success message
        CustomAlert.show(Alert.AlertType.INFORMATION,
                "Survey Started",
                "Plot Created Successfully",
                "New Plot ID: " + newPlot.getPlotID());

        // 8. Return to Surveyor Dashboard
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
