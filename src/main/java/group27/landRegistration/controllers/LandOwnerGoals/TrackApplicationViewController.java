package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class TrackApplicationViewController {

    @javafx.fxml.FXML
    private Label StatusResultL;
    @javafx.fxml.FXML
    private TextField TrackingIDTF;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    @javafx.fxml.FXML
    public void TrackOA(ActionEvent actionEvent) {

        String idText = TrackingIDTF.getText().trim();
        if (idText.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR,
                    "Missing Application ID",
                    "Please enter your application ID",
                    "The field cannot be empty.");
            return;
        }

        int appID;
        try {
            appID = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR,
                    "Invalid ID",
                    "Application ID must be numeric",
                    "Please enter a valid ID.");
            return;
        }

        // Load all applications
        FileManager<Application> appFM = new FileManager<>("Application.dat");
        List<Application> apps = appFM.loadList();
        Application found = null;

        for (Application a : apps) {
            if (a.getApplicationID() == appID) {
                found = a;
                break;
            }
        }

        if (found == null) {
            StatusResultL.setText("No application found with ID " + appID);
            return;
        }

        // Ensure this application belongs to the logged-in user
        if (found.getApplicantID() != loggedInUser.getUserID()) {
            CustomAlert.show(Alert.AlertType.ERROR,
                    "Access Denied",
                    "You cannot view this application",
                    "This application does not belong to your account.");
            return;
        }

        // Display Status + Remarks/Notes
        StringBuilder result = new StringBuilder();
        result.append("Status: ").append(found.getStatus());

        if (found.getNotes() != null && !found.getNotes().isEmpty()) {
            result.append("\n\nRemarks:\n").append(found.getNotes());
        }

        StatusResultL.setText(result.toString());
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
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
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
