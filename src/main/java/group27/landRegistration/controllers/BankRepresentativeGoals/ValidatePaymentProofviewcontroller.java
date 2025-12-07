package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.nonUsers.PaymentSlip;
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

public class ValidatePaymentProofviewcontroller {

    private User loggedInUser;

    @FXML
    private TextField TransactionIDTextField; // Input for identifying the transaction
    @FXML
    private TextArea MessageTextArea; // Input for validation notes/remarks

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void ValidateOnAction(ActionEvent actionEvent) {
        // 1. Validate Inputs
        String transIdText = TransactionIDTextField.getText();
        String remarks = MessageTextArea.getText();

        if (transIdText == null || transIdText.trim().isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Transaction ID", "Please enter the Transaction ID to validate.");
            return;
        }

        int transactionID;
        try {
            transactionID = Integer.parseInt(transIdText);
        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid ID", "Transaction ID must be a numeric value.");
            return;
        }

        // 2. Find the Payment Slip
        FileManager<PaymentSlip> fm = new FileManager<>("PaymentSlip.dat");
        List<PaymentSlip> slips = fm.loadList();

        PaymentSlip targetSlip = null;
        int index = -1;

        for (int i = 0; i < slips.size(); i++) {
            if (slips.get(i).getTransactionID() == transactionID) {
                targetSlip = slips.get(i);
                index = i;
                break;
            }
        }

        if (targetSlip == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Not Found", "Invalid Transaction", "No payment slip found with ID: " + transactionID);
            return;
        }

        // 3. Update Status
        if ("Verified".equalsIgnoreCase(targetSlip.getStatus())) {
            CustomAlert.show(Alert.AlertType.WARNING, "Already Verified", "No Action Needed", "This transaction is already marked as verified.");
            return;
        }

        targetSlip.setStatus("Verified");
        // Optionally append remarks to the status or a separate field if available
        // For now, we assume remarks are just for the log or local viewing context

        slips.set(index, targetSlip);
        fm.saveList(slips);

        // 4. Log Activity
        FileManager<SystemActivityLog> logFM = new FileManager<>("SystemActivity.dat");
        String logMessage = "Validated Proof for Trans ID " + transactionID + ". Note: " + (remarks.isEmpty() ? "None" : remarks);

        SystemActivityLog log = new SystemActivityLog(0, loggedInUser.getUserID(), logMessage);
        logFM.appendItem(log);

        // 5. Success
        CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Proof Validated", "The payment proof has been successfully validated and status updated.");
        GoBackOnAction(actionEvent);
    }

    @FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/BankRepresentativeDashboardView.fxml",
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