package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.controllers.AllDashboards.LandOwnerDashBoardViewController;
import group27.landRegistration.users.LandOwner;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.util.Map;

public class MyLandPortfolioViewController {

    @FXML private PieChart PortfolioPieChart;
    @FXML private RadioButton AreaRB;
    @FXML private RadioButton ValueRB;
    @FXML private ToggleGroup PortfolioTG;
    @FXML private Text SummaryText;

    private LandOwner loggedInOwner;
    private final ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        PortfolioPieChart.setData(chartData);
        PortfolioPieChart.setAnimated(false); // Disable animation to prevent glitches
        PortfolioPieChart.setLegendVisible(true);
        PortfolioPieChart.setLabelsVisible(false); // Hide labels to prevent overlap
    }

    public void setUserData(User user) {
        if (user instanceof LandOwner) {
            this.loggedInOwner = (LandOwner) user;
            LoadChartOA(null); // Load default view
        }
    }

    public User getLoggedInUser(){
        return loggedInOwner;
    }

    @FXML
    public void LoadChartOA(ActionEvent actionEvent) {
        if (loggedInOwner == null) return;

        boolean byValue = ValueRB.isSelected();

        // 1. Get Stats from Model
        Map<String, Double> stats = loggedInOwner.getPortfolioStatistics(byValue);

        // 2. Update Chart Data
        chartData.clear();
        double grandTotal = 0;

        for (Map.Entry<String, Double> entry : stats.entrySet()) {
            double value = entry.getValue();
            grandTotal += value;

            // Format label
            // e.g., "Plot 101 (2000.0)"
            String label = entry.getKey() + String.format(" (%,.0f)", value);

            if (value > 0) {
                chartData.add(new PieChart.Data(label, value));
            }
        }

        // 3. Update Titles & Summary
        if (byValue) {
            PortfolioPieChart.setTitle("Portfolio by Estimated Value (BDT)");
            SummaryText.setText(String.format("Total Portfolio Value: %,.0f BDT", grandTotal));
        } else {
            PortfolioPieChart.setTitle("Portfolio by Land Size (Sq.ft)");
            SummaryText.setText(String.format("Total Land Area Owned: %,.0f sq.ft", grandTotal));
        }

        if (chartData.isEmpty()) {
            SummaryText.setText("You do not own any registered plots yet.");
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(loggedInOwner);
                }
        );
    }
}