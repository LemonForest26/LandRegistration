package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class FinaliseTransferViewController {

    private LandRegistrar landReg;

    @FXML private TextField PlotID;
    @FXML private TextField NewOwnerIDTF;
    @FXML private TextField OldOwnerIDTF;

    // Use specific type if possible, or cast inside
    public void setUserData(User user) {
        if (user instanceof LandRegistrar) {
            this.landReg = (LandRegistrar) user;
        }
    }

    public User getLoggedInUser(){
        return landReg;
    }

    @FXML
    public void FinaliseTransferOA(ActionEvent actionEvent) {
        if (PlotID.getText().isEmpty() || OldOwnerIDTF.getText().isEmpty() || NewOwnerIDTF.getText().isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields", "All fields must be filled out.");
            return;
        }

        try {
            int plotID = Integer.parseInt(PlotID.getText());
            long oldOwnerID = Long.parseLong(OldOwnerIDTF.getText());
            long newOwnerID = Long.parseLong(NewOwnerIDTF.getText());

            // 1. Perform the Logic (Delegate to Model)
            landReg.finiliseTransfer(plotID, oldOwnerID, newOwnerID);

            // 2. Success Message
            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Transfer Finalized",
                    "Ownership transfer has been recorded successfully.");

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Invalid Input", "Invalid Number Format",
                    "Please enter valid numeric values for Plot ID and Owner IDs.");
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
                actionEvent,
                (LandRegistrarDashBoardViewController controller) -> {
                    controller.setUserData(landReg);
                }
        );
    }
}