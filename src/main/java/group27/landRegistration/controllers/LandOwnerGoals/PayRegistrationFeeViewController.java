package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.controllers.AllDashboards.LandOwnerDashBoardViewController;
import group27.landRegistration.nonUsers.PaymentSlip;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Random;

public class PayRegistrationFeeViewController {

    @FXML private ComboBox<String> PaymentMethodCB;
    @FXML private TextField PlotIDTF;

    private User loggedInUser;

    @FXML
    private void initialize() {
        // Populate Payment Methods
        PaymentMethodCB.setItems(FXCollections.observableArrayList(
                "Bkash", "Nagad", "Rocket", "Bank Transfer", "Credit Card"
        ));
    }

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void PayFeeOA(ActionEvent actionEvent) {
        // 1. Validation
        String plotText = PlotIDTF.getText().trim();
        String method = (String) PaymentMethodCB.getValue(); // Cast object to string safely

        if (plotText.isEmpty() || method == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please enter a Plot ID and select a payment method.");
            return;
        }

        try {
            int plotID = Integer.parseInt(plotText);

            // 2. Load Plot to Get Area
            FileManager<Plot> plotManager = new FileManager<>("Plot.dat");
            // Find the specific plot to read its UPDATED area
            Plot foundPlot = plotManager.find(p -> p.getPlotID() == plotID);

            if (foundPlot == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Invalid Plot",
                        "No plot found with ID: " + plotID);
                return;
            }

            // 3. DYNAMIC CALCULATION
            // Example Rule: Fee is 120 BDT per sq.ft (You can change this rate)
            double ratePerSqFt = 120.00;
            double currentArea = foundPlot.getArea();

            // Check if area is 0 (Surveyor hasn't measured it yet)
            if (currentArea <= 0) {
                CustomAlert.show(Alert.AlertType.WARNING, "Data Missing", "Area Not Measured",
                        "This plot has an area of 0. Please ask a Surveyor to measure it first.");
                return;
            }

            double calculatedFee = currentArea * ratePerSqFt;

            // Generate fake transaction ID
            int transactionID = 10000000 + new java.util.Random().nextInt(90000000);

            // 4. Create Payment Slip
            PaymentSlip newSlip = new PaymentSlip(
                    loggedInUser.getUserID(),
                    plotID,
                    transactionID,
                    calculatedFee, // <--- Using the dynamic fee now
                    method,
                    "Paid",
                    LocalDate.now()
            );

            // 5. Save
            FileManager<PaymentSlip> slipManager = new FileManager<>("PaymentSlip.dat");
            slipManager.appendItem(newSlip);

            // 6. Success Feedback
            CustomAlert.show(Alert.AlertType.INFORMATION, "Payment Successful",
                    "Transaction ID: " + transactionID,
                    "Plot Area: " + currentArea + " sq.ft\n" +
                            "Fee (" + ratePerSqFt + " BDT/sq.ft): " + calculatedFee + " BDT\n" +
                            "Paid successfully via " + method + ".");

            // Clear UI
            PlotIDTF.clear();
            PaymentMethodCB.getSelectionModel().clearSelection();

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Format",
                    "Plot ID must be a valid number.");
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Type-safe navigation back to dashboard
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(loggedInUser);
                }
        );
    }
}