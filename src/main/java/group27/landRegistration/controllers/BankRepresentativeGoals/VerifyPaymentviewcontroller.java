package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.nonUsers.PaymentSlip;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.List;

public class VerifyPaymentviewcontroller {

    private User loggedInUser;

    @FXML
    private TextField TransactionIDTextField;
    @FXML
    private Label PaymentStatusLabel;

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void VerifyOnAction(ActionEvent actionEvent) {
        // Reset label
        PaymentStatusLabel.setText("Checking...");
        PaymentStatusLabel.setTextFill(Color.BLACK);

        // 1. Validation
        String transIdText = TransactionIDTextField.getText();

        if (transIdText == null || transIdText.trim().isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Input", "Please enter a Transaction ID.");
            PaymentStatusLabel.setText("");
            return;
        }

        int transactionID;
        try {
            transactionID = Integer.parseInt(transIdText);
        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format", "Transaction ID must be numeric.");
            PaymentStatusLabel.setText("");
            return;
        }

        // 2. Load Data
        FileManager<PaymentSlip> fm = new FileManager<>("PaymentSlip.dat");
        List<PaymentSlip> slips = fm.loadList();

        PaymentSlip foundSlip = null;
        for (PaymentSlip slip : slips) {
            if (slip.getTransactionID() == transactionID) {
                foundSlip = slip;
                break;
            }
        }

        // 3. Display Result
        if (foundSlip != null) {
            String status = foundSlip.getStatus();
            PaymentStatusLabel.setText("Status: " + status);

            // Visual feedback based on status
            if ("Verified".equalsIgnoreCase(status) || "Paid".equalsIgnoreCase(status)) {
                PaymentStatusLabel.setTextFill(Color.GREEN);
            } else if ("Rejected".equalsIgnoreCase(status) || "Failed".equalsIgnoreCase(status)) {
                PaymentStatusLabel.setTextFill(Color.RED);
            } else {
                PaymentStatusLabel.setTextFill(Color.ORANGE);
            }
        } else {
            PaymentStatusLabel.setText("Status: Not Found");
            PaymentStatusLabel.setTextFill(Color.RED);
            CustomAlert.show(Alert.AlertType.WARNING, "Not Found", "No Record", "No payment slip found with ID: " + transactionID);
        }
    }

    @FXML
    public void BackToHomeOnAction(ActionEvent actionEvent) {
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