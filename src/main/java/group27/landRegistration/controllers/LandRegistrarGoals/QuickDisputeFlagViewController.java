package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.Surveyor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;
import java.util.stream.Collectors;

public class QuickDisputeFlagViewController {

    @FXML private ComboBox<User> SurveyorListCB;
    @FXML private TextArea ShortNoteTA;
    @FXML private TextField DisputeIDTF;

    private LandRegistrar loggedInRegistrar;

    @FXML
    private void initialize() {
        loadSurveyors();
    }

    public void setUserData(User user) {
        if (user instanceof LandRegistrar) {
            this.loggedInRegistrar = (LandRegistrar) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInRegistrar;
    }

    private void loadSurveyors() {
        FileManager<User> userFM = new FileManager<>("users.dat");
        List<User> allUsers = userFM.loadList();

        // Filter list to show ONLY Surveyors
        List<User> surveyors = allUsers.stream()
                .filter(u -> u instanceof Surveyor)
                .collect(Collectors.toList());

        SurveyorListCB.setItems(FXCollections.observableArrayList(surveyors));

        // Configure ComboBox to display names neatly
        SurveyorListCB.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user == null ? "" : user.getName() + " (ID: " + user.getUserID() + ")";
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    public void AssignSurveyorOA(ActionEvent actionEvent) {
        // 1. Validation
        String idText = DisputeIDTF.getText().trim();
        User selectedSurveyor = SurveyorListCB.getValue();
        String note = ShortNoteTA.getText().trim();

        if (idText.isEmpty() || selectedSurveyor == null || note.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please fill in the Dispute ID, select a Surveyor, and provide a short note.");
            return;
        }

        try {
            int disputeID = Integer.parseInt(idText);

            // 2. Delegate to Model
            loggedInRegistrar.assignSurveyorToDispute(disputeID, selectedSurveyor.getUserID(), note);

            // 3. Success Feedback
            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Surveyor Assigned",
                    "Surveyor " + selectedSurveyor.getName() + " has been assigned to Dispute ID " + disputeID);

            // Clear fields
            DisputeIDTF.clear();
            ShortNoteTA.clear();
            SurveyorListCB.getSelectionModel().clearSelection();

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Dispute ID must be a number.");
        } catch (Exception e) {
            CustomAlert.show(Alert.AlertType.ERROR, "System Error", "Operation Failed",
                    e.getMessage());
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