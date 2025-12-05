package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ApproveRejectApplicationViewController {

    @FXML private TextField PlotIDTF;
    @FXML private TextField ApplicationIDTF;
    @FXML private TextArea RemarksTF;

    private LandRegistrar loggedInRegistrar;
    private Application currentApplication;

    public void setUserData(User user) {
        if (user instanceof LandRegistrar) {
            this.loggedInRegistrar = (LandRegistrar) user;
        }
    }

    public void setApplicationData(Application app) {
        this.currentApplication = app;
        if (app != null) {
            PlotIDTF.setText(String.valueOf(app.getPlotID()));
            ApplicationIDTF.setText(String.valueOf(app.getApplicationID()));

            // Make ID fields read-only so they don't accidentally change the wrong one
            PlotIDTF.setEditable(false);
            ApplicationIDTF.setEditable(false);
        }
    }

    public User getLoggedInUser(){
        return loggedInRegistrar;
    }

    @FXML
    public void ApproveOA(ActionEvent actionEvent) {
        processDecision(actionEvent, "Approved");
    }

    @FXML
    public void RejectOA(ActionEvent actionEvent) {
        if (RemarksTF.getText().trim().isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Required", "Reason Missing",
                    "Please provide remarks/reason for rejection.");
            return;
        }
        processDecision(actionEvent, "Rejected");
    }

    private void processDecision(ActionEvent event, String status) {
        // Validation
        if (currentApplication == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Error", "No Application Loaded",
                    "Please go back and select an application properly.");
            return;
        }

        try {
            String remarks = RemarksTF.getText().trim();

            // DELEGATION: Ask model to save everything
            loggedInRegistrar.processApplication(currentApplication, status, remarks);

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Application " + status,
                    "The application status has been updated.");

            BackOA(event);

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Update Failed", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // IMPORTANT: Ensure this path points to your actual FXML file location
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/LandRegistrarGoals/ReviewPendingApplicationView.fxml",
                actionEvent,
                (ReviewPendingApplicationViewController controller) -> {
                    controller.setUserData(loggedInRegistrar);
                }
        );
    }
}