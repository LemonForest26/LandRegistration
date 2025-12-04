package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.controllers.AllDashboards.LandOwnerDashBoardViewController;
import group27.landRegistration.nonUsers.Mutation;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.LandOwner;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class RequestCorrectionMutationViewController {

    @FXML private TextField PlotIDTF;
    @FXML private TextArea CorrectionTA;

    private LandOwner loggedInUser;

    // Cast the generic User to LandOwner for specific logic
    public void setUserData(User user) {
        if (user instanceof LandOwner) {
            this.loggedInUser = (LandOwner) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void SubmitCorrectionOA(ActionEvent actionEvent) {
        // 1. Validation
        String plotText = PlotIDTF.getText().trim();
        String correctionDetails = CorrectionTA.getText().trim();

        if (plotText.isEmpty() || correctionDetails.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please enter the Plot ID and the correction details.");
            return;
        }

        try {
            int plotID = Integer.parseInt(plotText);

            // 2. Load Plots to Verify Ownership
            FileManager<Plot> plotManager = new FileManager<>("Plot.dat");
            List<Plot> allPlots = plotManager.loadList();

            Plot targetPlot = null;
            int plotIndex = -1;

            // Search for the plot
            for (int i = 0; i < allPlots.size(); i++) {
                if (allPlots.get(i).getPlotID() == plotID) {
                    targetPlot = allPlots.get(i);
                    plotIndex = i;
                    break;
                }
            }

            // 3. Logic Checks
            if (targetPlot == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Plot Not Found",
                        "No plot exists with ID: " + plotID);
                return;
            }

            if (targetPlot.getOwnerID() != loggedInUser.getUserID()) {
                CustomAlert.show(Alert.AlertType.ERROR, "Security Warning", "Unauthorized",
                        "You cannot request a correction for a plot you do not own.");
                return;
            }

            // 4. Create Mutation Object
            // Using the constructor: Mutation(int plotID, int ownerID, String correctionText)
            Mutation newMutation = new Mutation(plotID, loggedInUser.getUserID(), correctionDetails);

            // 5. Update Mutation.dat (Master Record)
            FileManager<Mutation> mutationManager = new FileManager<>("Mutation.dat");
            mutationManager.appendItem(newMutation);

            // 6. Update Plot.dat (Link Mutation to Plot)
            // We modify the plot object and save the list back
            targetPlot.addMutation(newMutation); // Assumes Plot class has this method
            allPlots.set(plotIndex, targetPlot); // Replace old plot with updated one
            plotManager.saveList(allPlots);      // Save back to file

            // 7. Success Feedback
            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Request Submitted",
                    "Your correction request has been filed. Mutation ID: " + newMutation.getMutationID());

            // Clear fields
            CorrectionTA.clear();
            PlotIDTF.clear();

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Plot ID must be a numeric value.");
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Use the Clean Navigation method to avoid ClassCastException/Reflection errors
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(loggedInUser);
                }
        );
    }
}