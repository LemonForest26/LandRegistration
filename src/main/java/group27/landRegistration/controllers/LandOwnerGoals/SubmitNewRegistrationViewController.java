package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.controllers.AllDashboards.LandOwnerDashBoardViewController;
import group27.landRegistration.users.LandOwner;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.*;
import group27.landRegistration.nonUsers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class SubmitNewRegistrationViewController {

    private LandOwner landOwner;

    @FXML private TextField PlotIDTF;
    @FXML private TextField AddressTF;
    @FXML private TextArea DocumentsTextTA;

    public void setUserData(User user) {
        if (user instanceof LandOwner) {
            this.landOwner = (LandOwner) user;
        }
    }

    public User getLoggedInUser(){
        return landOwner;
    }

    @FXML
    public void SubmitRegistrationOA(ActionEvent actionEvent) {
        // 0. Pre-check: Ensure user is logged in
        if (landOwner == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Session Error", "No User",
                    "Please login again.");
            return;
        }

        String plotIDText = PlotIDTF.getText().trim();
        String address = AddressTF.getText().trim();
        String docsText = DocumentsTextTA.getText().trim();

        if (plotIDText.isEmpty() || address.isEmpty() || docsText.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Missing Information",
                    "All fields are required", "Please fill up all the fields.");
            return;
        }

        int plotID;
        try {
            plotID = Integer.parseInt(plotIDText);
        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Invalid Plot ID",
                    "Invalid Input", "Plot ID must be a number.");
            return;
        }

        // 1. Load plots safely
        FileManager<Plot> plotFM = new FileManager<>("Plot.dat");
        List<Plot> plots = plotFM.loadList();

        System.out.println("DEBUG: Looking for Plot ID: " + plotID);
        if (plots == null || plots.isEmpty()) {
            System.out.println("DEBUG: Plot list is empty or null.");
            CustomAlert.show(Alert.AlertType.ERROR, "Data Error",
                    "No Plots Found", "The system has no plot records yet. Please ensure a Surveyor has created plots.");
            return;
        } else {
            System.out.println("DEBUG: Loaded " + plots.size() + " plots from file.");
        }

        // 2. Find the plot and Validate Ownership
        Plot selectedPlot = null;
        for (Plot p : plots) {
            System.out.println("DEBUG: Checking file Plot ID: " + p.getPlotID() + " (Owner: " + p.getOwnerID() + ")");
            if (p.getPlotID() == plotID) {
                selectedPlot = p;
                break;
            }
        }

        if (selectedPlot == null) {
            System.out.println("DEBUG: Plot ID " + plotID + " was not found in the list.");
            CustomAlert.show(Alert.AlertType.ERROR, "Plot Not Found",
                    "Invalid Plot", "No plot with ID " + plotID + " exists.");
            return;
        }

        // Check if the logged-in LandOwner actually owns this plot
        System.out.println("DEBUG: Selected Plot Owner ID: " + selectedPlot.getOwnerID());
        System.out.println("DEBUG: Logged in User ID: " + landOwner.getUserID());

        if (selectedPlot.getOwnerID() != landOwner.getUserID()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Access Denied",
                    "Ownership Mismatch",
                    "You are not listed as the owner of Plot ID " + plotID + ".\nPlot belongs to ID: " + selectedPlot.getOwnerID());
            return;
        }

        try {
            // 3. Create Application
            Application app = new Application(
                    plotID,
                    landOwner.getUserID(),
                    "Pending",
                    "New registration request for: " + address
            );

            // Add attachments
            for (String doc : docsText.split(",")) {
                if (!doc.trim().isEmpty()) {
                    app.addAttachment(doc.trim());
                }
            }

            // 4. Save Application
            FileManager<Application> applicationFM = new FileManager<>("Application.dat");
            // Load existing applications to append correctly (read-modify-write pattern preferred for reliability)
            List<Application> apps = applicationFM.loadList();
            if (apps == null) apps = new ArrayList<>();
            apps.add(app);
            applicationFM.saveList(apps);
            // Note: using saveList instead of appendItem prevents corruption in some ObjectOutputStream implementations

            // 5. Update Plot with the new Application reference
            selectedPlot.addApplication(app);

            // Replace the old plot object in the list
            for(int i=0; i<plots.size(); i++) {
                if(plots.get(i).getPlotID() == selectedPlot.getPlotID()){
                    plots.set(i, selectedPlot);
                    break;
                }
            }
            plotFM.saveList(plots);

            CustomAlert.show(Alert.AlertType.INFORMATION,
                    "Success",
                    "Registration Submitted",
                    "Your application ID: " + app.getApplicationID());

            // Navigation
            navigateBack(actionEvent);

        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error",
                    "Submission Failed", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }

    private void navigateBack(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(landOwner);
                }
        );
    }
}