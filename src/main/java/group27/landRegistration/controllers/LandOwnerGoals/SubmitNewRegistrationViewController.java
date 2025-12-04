package group27.landRegistration.controllers.LandOwnerGoals;

// 1. FIXED IMPORT: Import the correct Dashboard Controller
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

import java.time.LocalDate;
import java.util.List;

public class SubmitNewRegistrationViewController {

    private LandOwner landOwner;

    @FXML private TextField PlotIDTF;
    @FXML private TextField AddressTF;
    @FXML private TextArea DocumentsTextTA;

    // Keep signature generic (User) to allow easy passing from previous screens
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

        // Load plots
        FileManager<Plot> plotFM = new FileManager<>("Plot.dat");
        List<Plot> plots = plotFM.loadList();

        Plot selectedPlot = null;
        for (Plot p : plots) {
            if (p.getPlotID() == plotID) {
                selectedPlot = p;
                break;
            }
        }

        if (selectedPlot == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Plot Not Found",
                    "Invalid Plot", "No plot with ID " + plotID + " exists.");
            return;
        }

        // Create application
        Application app = new Application(
                plotID,
                landOwner.getUserID(),
                "Pending",
                "New registration request",
                LocalDate.now(),
                LocalDate.now()
        );

        // Add attachments
        for (String doc : docsText.split(",")) {
            app.getAttachments().add(doc.trim());
        }

        // Save application
        FileManager<Application> applicationFM = new FileManager<>("Application.dat");
        applicationFM.appendItem(app);

        // Add application to plot and update plot file
        selectedPlot.addApplication(app);

        // Find index to replace the old plot object with the new one containing the application
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

        // FIXED NAVIGATION (Success Case)
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(landOwner);
                }
        );
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // FIXED NAVIGATION (Back Button Case)
        // Changed LandRegistrarDashBoardViewController -> LandOwnerDashBoardViewController
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(landOwner);
                }
        );
    }
}