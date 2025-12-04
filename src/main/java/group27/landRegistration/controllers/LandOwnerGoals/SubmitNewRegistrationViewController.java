package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.*;
import group27.landRegistration.nonUsers.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;

public class SubmitNewRegistrationViewController {
    @javafx.fxml.FXML
    private TextField PlotIDTF;
    @javafx.fxml.FXML
    private TextField AddressTF;
    @javafx.fxml.FXML
    private TextArea DocumentsTextTA;
    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }


    @javafx.fxml.FXML
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
        FileManager<Plot> plotFM = new FileManager<>("data/plots.dat");
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
                loggedInUser.getUserID(),
                "Pending",
                "New registration request",
                LocalDate.now(),
                LocalDate.now()
        );

        // Add attachments (comma separated)
        for (String doc : docsText.split(",")) {
            app.getAttachments().add(doc.trim());
        }

        // Save application
        FileManager<Application> applicationFM = new FileManager<>("data/applications.dat");
        applicationFM.appendItem(app);

        // Add application to plot
        selectedPlot.addApplication(app);
        plotFM.saveList(plots);

        CustomAlert.show(Alert.AlertType.INFORMATION,
                "Success",
                "Registration Submitted",
                "Your application ID: " + app.getApplicationID());

        // Return to dashboard
        CurrentPageLoader loader = new CurrentPageLoader();
        loader.loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
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
    }

}
