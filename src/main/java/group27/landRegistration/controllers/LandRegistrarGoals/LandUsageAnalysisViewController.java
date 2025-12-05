package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
import group27.landRegistration.users.LandRegistrar;
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

public class LandUsageAnalysisViewController {

    @FXML private PieChart UsagePieChart;
    @FXML private RadioButton CountRB;
    @FXML private RadioButton AreaRB;
    @FXML private ToggleGroup AnalysisTG;
    @FXML private Text SummaryText;

    private LandRegistrar loggedInRegistrar;
    private final ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        UsagePieChart.setData(chartData);
        UsagePieChart.setAnimated(false); // Disable animation to prevent glitches
        UsagePieChart.setLegendVisible(true);
        UsagePieChart.setLabelsVisible(false); // Hide overlapping labels
    }

    public void setUserData(User user) {
        if (user instanceof LandRegistrar) {
            this.loggedInRegistrar = (LandRegistrar) user;
            LoadChartOA(null); // Load default view
        }
    }

    public User getLoggedInUser(){
        return loggedInRegistrar;
    }

    @FXML
    public void LoadChartOA(ActionEvent actionEvent) {
        if (loggedInRegistrar == null) return;

        boolean byArea = AreaRB.isSelected();

        // 1. Get Stats from Model
        Map<String, Double> stats = loggedInRegistrar.getZoningStatistics(byArea);

        // 2. Update Chart Data
        chartData.clear();
        double grandTotal = 0;

        for (Map.Entry<String, Double> entry : stats.entrySet()) {
            double value = entry.getValue();
            grandTotal += value;

            // Format label depending on type
            String label = entry.getKey();
            if (byArea) {
                // e.g., "Residential (5000.0)"
                label += String.format(" (%.0f sq.ft)", value);
            } else {
                // e.g., "Residential (5)"
                label += String.format(" (%.0f)", value);
            }

            if (value > 0) {
                chartData.add(new PieChart.Data(label, value));
            }
        }

        // 3. Update Titles
        if (byArea) {
            UsagePieChart.setTitle("Zoning by Total Area");
            SummaryText.setText(String.format("Total Registered Land Area: %,.0f sq.ft", grandTotal));
        } else {
            UsagePieChart.setTitle("Zoning by Plot Count");
            SummaryText.setText(String.format("Total Registered Plots: %.0f", grandTotal));
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