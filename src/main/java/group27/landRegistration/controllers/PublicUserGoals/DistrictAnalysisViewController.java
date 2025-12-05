package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import group27.landRegistration.users.PublicUser;
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

public class DistrictAnalysisViewController {

    @FXML private PieChart DistrictPieChart;
    @FXML private RadioButton CountRB;
    @FXML private RadioButton AreaRB;
    @FXML private ToggleGroup AnalysisTG;
    @FXML private Text SummaryText;

    private PublicUser loggedInPublicUser;
    private final ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        DistrictPieChart.setData(chartData);
        DistrictPieChart.setAnimated(false); // Disable animation for stability
        DistrictPieChart.setLegendVisible(true);
        DistrictPieChart.setLabelsVisible(false); // Hide labels to keep it clean
    }

    public void setUserData(User user) {
        if (user instanceof PublicUser) {
            this.loggedInPublicUser = (PublicUser) user;
            LoadChartOA(null); // Load default view
        }
    }

    public User getLoggedInUser(){
        return loggedInPublicUser;
    }

    @FXML
    public void LoadChartOA(ActionEvent actionEvent) {
        if (loggedInPublicUser == null) return;

        boolean byArea = AreaRB.isSelected();

        // 1. Get Stats from Model
        Map<String, Double> stats = loggedInPublicUser.getDistrictStatistics(byArea);

        // 2. Update Chart
        chartData.clear();
        double grandTotal = 0;

        for (Map.Entry<String, Double> entry : stats.entrySet()) {
            double value = entry.getValue();
            grandTotal += value;

            String label = entry.getKey();

            // Format Label based on mode
            if (byArea) {
                label += String.format(" (%.0f sq.ft)", value);
            } else {
                label += String.format(" (%.0f plots)", value);
            }

            if (value > 0) {
                chartData.add(new PieChart.Data(label, value));
            }
        }

        // 3. Update Text Info
        if (byArea) {
            DistrictPieChart.setTitle("Regional Distribution by Total Area");
            SummaryText.setText(String.format("Total Registered Land Area in System: %,.0f sq.ft", grandTotal));
        } else {
            DistrictPieChart.setTitle("Regional Distribution by Plot Count");
            SummaryText.setText(String.format("Total Registered Plots in System: %.0f", grandTotal));
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml",
                actionEvent,
                (PublicUserDashBoardViewController controller) -> {
                    controller.setUserData(loggedInPublicUser);
                }
        );
    }
}