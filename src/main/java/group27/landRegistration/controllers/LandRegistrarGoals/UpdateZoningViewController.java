package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UpdateZoningViewController {

    @FXML private TextField PlotIDTF;
    @FXML private TextArea FileUploadTA; // Used for Document Reference / Remarks
    @FXML private ComboBox<String> NewZoningCB;

    private LandRegistrar loggedInRegistrar;

    @FXML
    private void initialize() {
        // Populate standard zoning types
        NewZoningCB.setItems(FXCollections.observableArrayList(
                "Residential",
                "Commercial",
                "Industrial",
                "Agricultural",
                "Mixed Use",
                "Public/Government"
        ));
    }

    public void setUserData(User user) {
        if (user instanceof LandRegistrar) {
            this.loggedInRegistrar = (LandRegistrar) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInRegistrar;
    }

    @FXML
    public void SaveZoningOA(ActionEvent actionEvent) {
        // 1. Validation
        String plotText = PlotIDTF.getText().trim();
        String zoning = NewZoningCB.getValue();
        String docRef = FileUploadTA.getText().trim();

        if (plotText.isEmpty() || zoning == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please enter a Plot ID and select a new Zoning type.");
            return;
        }

        try {
            int plotID = Integer.parseInt(plotText);

            // 2. Delegate to Model
            boolean success = loggedInRegistrar.updatePlotZoning(plotID, zoning, docRef);

            // 3. Feedback
            if (success) {
                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Zoning Updated",
                        "Plot " + plotID + " is now classified as " + zoning + ".");

                // Clear fields
                PlotIDTF.clear();
                NewZoningCB.getSelectionModel().clearSelection();
                FileUploadTA.clear();
            } else {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Plot Not Found",
                        "No plot exists with ID: " + plotID);
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Plot ID must be a number.");
        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Update Failed",
                    e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Type-safe navigation
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
                actionEvent,
                (LandRegistrarDashBoardViewController controller) -> {
                    controller.setUserData(loggedInRegistrar);
                }
        );
    }
}