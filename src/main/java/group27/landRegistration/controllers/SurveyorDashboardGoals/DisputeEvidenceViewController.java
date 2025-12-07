package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.nonUsers.Dispute;
import group27.landRegistration.nonUsers.SystemActivityLog;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class DisputeEvidenceViewController {

    @FXML
    private TextArea FileUploadTextArea;
    @FXML
    private TextField DisputeIDTextField;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void UploadEvidenceOnAction(ActionEvent actionEvent) {
        // 1. Validation
        String disputeIdText = DisputeIDTextField.getText();
        String evidenceData = FileUploadTextArea.getText();

        if (disputeIdText.isEmpty() || evidenceData.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Validation Error", "Missing Fields", "Please enter a Dispute ID and Evidence details.");
            return;
        }

        int disputeID;
        try {
            disputeID = Integer.parseInt(disputeIdText);
        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Validation Error", "Invalid ID", "Dispute ID must be a number.");
            return;
        }

        // 2. Load Disputes
        FileManager<Dispute> fm = new FileManager<>("Dispute.dat");
        List<Dispute> disputes = fm.loadList();

        Dispute targetDispute = null;
        int index = -1;

        // 3. Find Dispute
        for (int i = 0; i < disputes.size(); i++) {
            if (disputes.get(i).getDisputeID() == disputeID) {
                targetDispute = disputes.get(i);
                index = i;
                break;
            }
        }

        if (targetDispute == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Not Found", "Invalid Dispute", "No dispute found with ID: " + disputeID);
            return;
        }

        // 4. Verify Authorization (Optional: Check if this surveyor is assigned)
        if (targetDispute.getAssignedSurveyorID() != loggedInUser.getUserID()) {
            CustomAlert.show(Alert.AlertType.WARNING, "Unauthorized", "Wrong Assignment", "You are not the assigned surveyor for this dispute.");
            return;
        }

        // 5. Update Dispute (Append evidence to notes/status)
        // We assume we are appending to the internal notes or status description
        // Ideally, Dispute class should have an addEvidence() method.
        // Here we simulate it by updating the status or printing to console/log if method missing.
        // Assuming we update the status to indicate evidence is provided:
        targetDispute.setStatus("Evidence Uploaded: " + (evidenceData.length() > 20 ? evidenceData.substring(0, 20) + "..." : evidenceData));

        disputes.set(index, targetDispute);
        fm.saveList(disputes);

        // 6. Log Activity
        FileManager<SystemActivityLog> logFM = new FileManager<>("SystemActivity.dat");
        SystemActivityLog log = new SystemActivityLog(0, loggedInUser.getUserID(), "Uploaded evidence for Dispute ID " + disputeID);
        logFM.appendItem(log);

        // 7. Success
        CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Evidence Uploaded", "The evidence has been recorded for the dispute.");
        GoBackOnAction(actionEvent);
    }

    @FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml",
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