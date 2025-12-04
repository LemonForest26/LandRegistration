package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class FinaliseTransferViewController {

    private LandRegistrar loggedInUser;
    @javafx.fxml.FXML
    private TextField PlotID;
    @javafx.fxml.FXML
    private TextField NewOwnerIDTF;
    @javafx.fxml.FXML
    private TextField OldOwnerIDTF;

    public void setUserData(LandRegistrar user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    public void FinaliseTransferOA(ActionEvent actionEvent) {
        if (PlotID.getText().isEmpty() || OldOwnerIDTF.getText().isEmpty() || NewOwnerIDTF.getText().isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR,
                    "Input Error",
                    "Missing Fields",
                    "All fields must be filled out.");
            return;
        }
        try {
            int plotID = Integer.parseInt(PlotID.getText());
            long oldOwnerID = Long.parseLong(OldOwnerIDTF.getText());
            long newOwnerID = Long.parseLong(NewOwnerIDTF.getText());

            loggedInUser.finiliseTransfer(plotID, oldOwnerID, newOwnerID);
        }
        catch (NumberFormatException e) {
            // If parsing fails, show an error
            CustomAlert.show(Alert.AlertType.ERROR,
                    "Invalid Input",
                    "Invalid Number Format",
                    "Please enter valid numeric values for Plot ID and Owner IDs.");
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
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
