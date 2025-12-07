package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.nonUsers.PaymentSlip;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ReconcileTransactionViewController {

    private User loggedInUser;

    @FXML private TableView<ReconciliationEntry> ReconciliationTableView;
    @FXML private TableColumn<ReconciliationEntry, String> RegistryStatusTablecolumn;
    @FXML private TableColumn<ReconciliationEntry, String> BankstatusTablecolumn;
    @FXML private TableColumn<ReconciliationEntry, String> MatchTableColumn;
    @FXML private TableColumn<ReconciliationEntry, String> TransactionIDColumn; // Ideally add this to FXML for clarity

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    private void initialize() {
        // 1. Configure Columns
        // Note: I've added a Transaction ID column conceptually, but if your FXML doesn't have it,
        // you can ignore adding it to the TableView or add it to the FXML file.
        // If FXML only has the 3 columns provided in your snippet:

        RegistryStatusTablecolumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRegistryStatus()));
        BankstatusTablecolumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBankStatus()));
        MatchTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMatchStatus()));

        // 2. Load Data
        loadReconciliationData();
    }

    private void loadReconciliationData() {
        // Load Bank Records (Payment Slips)
        FileManager<PaymentSlip> slipManager = new FileManager<>("PaymentSlip.dat");
        List<PaymentSlip> slips = slipManager.loadList();

        // Load Registry Records (Applications)
        FileManager<Application> appManager = new FileManager<>("Application.dat");
        List<Application> apps = appManager.loadList();

        ObservableList<ReconciliationEntry> entries = FXCollections.observableArrayList();

        // 3. Compare Data
        // Logic: For every Payment Slip, try to find a corresponding Application for the same Plot & Owner
        for (PaymentSlip slip : slips) {
            String bankStatus = slip.getStatus(); // e.g., "Paid", "Pending"
            String registryStatus = "Not Found";
            String match = "No";

            // Find matching application based on Plot ID and Owner ID
            for (Application app : apps) {
                if (app.getPlotID() == slip.getPlotID() && app.getApplicantID() == slip.getOwnerID()) {
                    registryStatus = app.getStatus(); // e.g., "Approved", "Pending"
                    break;
                }
            }

            // Simple Matching Logic
            // If Bank says "Paid" and Registry says "Approved" or "Pending", we might consider that a match contextually,
            // or we strictly check strings. Let's do a loose check for demonstration.
            if ("Paid".equalsIgnoreCase(bankStatus) && !"Rejected".equalsIgnoreCase(registryStatus) && !"Not Found".equals(registryStatus)) {
                match = "Likely Match";
            } else if (bankStatus.equalsIgnoreCase(registryStatus)) {
                match = "Exact Match";
            } else {
                match = "Mismatch";
            }

            entries.add(new ReconciliationEntry(
                    String.valueOf(slip.getTransactionID()),
                    registryStatus,
                    bankStatus,
                    match
            ));
        }

        ReconciliationTableView.setItems(entries);
    }

    @FXML
    public void gobackOnAction(ActionEvent actionEvent) {
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

    // --- Inner Class for Table Data ---
    public static class ReconciliationEntry {
        private final String transactionID;
        private final String registryStatus;
        private final String bankStatus;
        private final String matchStatus;

        public ReconciliationEntry(String transactionID, String registryStatus, String bankStatus, String matchStatus) {
            this.transactionID = transactionID;
            this.registryStatus = registryStatus;
            this.bankStatus = bankStatus;
            this.matchStatus = matchStatus;
        }

        public String getRegistryStatus() { return registryStatus; }
        public String getBankStatus() { return bankStatus; }
        public String getMatchStatus() { return matchStatus; }
        public String getTransactionID() { return transactionID; }
    }
}