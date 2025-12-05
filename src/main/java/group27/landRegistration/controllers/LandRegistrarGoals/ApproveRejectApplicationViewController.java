package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager; // Need this for lookup
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

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

            // Set Read-Only to encourage selecting from the list,
            // but you can remove these lines if you prefer manual entry.
            PlotIDTF.setEditable(false);
            ApplicationIDTF.setEditable(false);
        }
    }

    public User getLoggedInUser(){
        return loggedInRegistrar;
    }

    // --- HELPER TO RECOVER APP FROM TEXT FIELDS ---
    private boolean ensureApplicationLoaded() {
        if (currentApplication != null) return true;

        // Try to recover using the text fields
        try {
            String appIDText = ApplicationIDTF.getText().trim();
            if (appIDText.isEmpty()) return false;

            int appID = Integer.parseInt(appIDText);

            // Search in file
            FileManager<Application> fm = new FileManager<>("Application.dat");
            List<Application> apps = fm.loadList();

            for (Application app : apps) {
                if (app.getApplicationID() == appID) {
                    this.currentApplication = app; // Recovered!
                    return true;
                }
            }
        } catch (Exception e) {
            // Ignore parsing errors, just return false
        }
        return false;
    }

    @FXML
    public void ApproveOA(ActionEvent actionEvent) {
        // 1. Recover/Validate
        if (!ensureApplicationLoaded()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Error", "No Application Loaded",
                    "Could not find an application with the given ID. Please select one from the list.");
            return;
        }

        try {
            // 2. Logic
            String remarks = RemarksTF.getText().trim();
            // If remarks are empty for approval, add default text
            if(remarks.isEmpty()) remarks = "Approved by Land Registrar";

            loggedInRegistrar.processApplication(currentApplication, "Approved", remarks);

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Application Approved",
                    "Status updated to Approved.");

            BackOA(actionEvent);

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Update Failed", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void RejectOA(ActionEvent actionEvent) {
        // 1. Recover/Validate
        if (!ensureApplicationLoaded()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Error", "No Application Loaded",
                    "Could not find an application with the given ID.");
            return;
        }

        String remarks = RemarksTF.getText().trim();
        if (remarks.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Required", "Reason Missing",
                    "Please provide remarks for rejection.");
            return;
        }

        try {
            // 2. Logic
            loggedInRegistrar.processApplication(currentApplication, "Rejected", remarks);

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Application Rejected",
                    "Status updated to Rejected.");

            BackOA(actionEvent);

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Update Failed", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
                actionEvent,
                (LandRegistrarDashBoardViewController controller) -> {
                    controller.setUserData(loggedInRegistrar);
                }
        );
    }
}