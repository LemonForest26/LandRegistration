package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.nonUsers.SystemActivityLog;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.List;

public class ApproveMortgageviewcontroller {

    private User loggedInUser;
    private Application currentApp;
    private final FileManager<Application> appManager = new FileManager<>("Application.dat");
    private final FileManager<SystemActivityLog> logManager = new FileManager<>("SystemActivity.dat");

    @FXML private Label ApplicationNameTextfield;
    @FXML private Label LoanAmountTextField;
    @FXML private Label plotIDtextfield;
    @FXML private Label statusLabel;

    public void setUserData(User user) {
        this.loggedInUser = user;
        loadFirstPendingApplication();
    }

    private void loadFirstPendingApplication() {
        List<Application> apps = appManager.loadList();

        // Find first application that isn't approved/rejected AND is actually a mortgage (loan > 0)
        for (Application app : apps) {
            if (!"Approved".equalsIgnoreCase(app.getStatus())
                    && !"Rejected".equalsIgnoreCase(app.getStatus())
                    && app.getLoanAmount() > 0) { // Critical check: Ignore regular mutation/registration apps
                this.currentApp = app;
                updateUI();
                return;
            }
        }

        // If no mortgage application found, create a dummy one for testing
        if (currentApp == null) {
            // Using Constructor 1 (Mortgage) to ensure it has a loan amount
            currentApp = new Application(505, 1002, "Pending Review", "Urgent Loan", 500000.00);
            appManager.appendItem(currentApp);
            updateUI();
        }
    }

    private void updateUI() {
        if (currentApp != null) {
            ApplicationNameTextfield.setText(currentApp.getApplicantName());
            plotIDtextfield.setText(String.valueOf(currentApp.getPlotID()));
            LoanAmountTextField.setText(currentApp.getLoanAmount() + " BDT");
            statusLabel.setText(currentApp.getStatus());
        } else {
            statusLabel.setText("No Pending Mortgages");
            LoanAmountTextField.setText("0.00 BDT");
        }
    }

    @FXML
    public void SubmitOnAction(ActionEvent actionEvent) {
        if (currentApp == null) return;

        // 1. Update Object Status
        currentApp.setStatus("Approved");

        // 2. Update File
        List<Application> apps = appManager.loadList();
        boolean found = false;
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getApplicationID() == currentApp.getApplicationID()) {
                appManager.updateItem(i, currentApp);
                found = true;
                break;
            }
        }

        if (!found) {
            CustomAlert.show(Alert.AlertType.ERROR, "Error", "Update Failed", "Could not find original application to update.");
            return;
        }

        // 3. Log Activity
        logManager.appendItem(new SystemActivityLog(0, loggedInUser.getUserID(), "Approved Mortgage ID: " + currentApp.getApplicationID()));

        CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Mortgage Approved", "The loan has been approved.");
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
                            controller.getClass().getMethod("setUserData", User.class).invoke(controller, loggedInUser);
                        } catch (Exception e) { e.printStackTrace(); }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}