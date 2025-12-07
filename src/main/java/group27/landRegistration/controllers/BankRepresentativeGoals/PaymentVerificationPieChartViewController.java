package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.nonUsers.PaymentSlip;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentVerificationPieChartViewController {

    private User loggedInUser;

    @FXML
    private PieChart PaymentVerificationPieChart;

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void initialize() {
        // Automatically load chart data when the view opens
        loadChartData();
    }

    private void loadChartData() {
        // 1. Load all payment slips from file
        FileManager<PaymentSlip> fm = new FileManager<>("PaymentSlip.dat");
        List<PaymentSlip> slips = fm.loadList();

        // 2. Process data: Count occurrences of each status
        Map<String, Integer> statusCounts = new HashMap<>();

        for (PaymentSlip slip : slips) {
            String status = slip.getStatus();

            // Handle null or empty statuses safely
            if (status == null || status.trim().isEmpty()) {
                status = "Unknown";
            }

            // Normalize status strings (optional, keeps chart clean)
            status = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();

            statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
        }

        // 3. Convert map to PieChart.Data
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
            // Label format: "Verified (5)"
            String label = entry.getKey() + " (" + entry.getValue() + ")";
            pieData.add(new PieChart.Data(label, entry.getValue()));
        }

        // 4. Bind data to the chart
        PaymentVerificationPieChart.setData(pieData);
        PaymentVerificationPieChart.setTitle("Payment Status Distribution");
    }

    @FXML
    public void BackOnAction(ActionEvent actionEvent) {
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

    @FXML
    public void PaymentVerificationPieChartOnAction(ActionEvent actionEvent) {
        // This button acts as a "Refresh" button
        loadChartData();
    }
}