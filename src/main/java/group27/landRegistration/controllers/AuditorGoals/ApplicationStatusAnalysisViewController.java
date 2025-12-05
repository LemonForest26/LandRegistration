package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.users.Auditor;
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

public class ApplicationStatusAnalysisViewController {

    @FXML private PieChart StatusPieChart;
    @FXML private RadioButton AllTimeRB;
    @FXML private RadioButton ThisYearRB;
    @FXML private RadioButton ThisMonthRB;
    @FXML private ToggleGroup TimeFilterTG;
    @FXML private Text SummaryText;

    private Auditor loggedInAuditor;

    // Data list must be static/final to prevent memory leaks and UI glitches
    private final ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        StatusPieChart.setData(chartData);
        StatusPieChart.setAnimated(false);
        StatusPieChart.setLegendVisible(true);
        StatusPieChart.setLabelsVisible(false);
        StatusPieChart.setData(chartData);

        new group27.landRegistration.users.Auditor("temp", "temp", "temp", "temp", 0, 0, null).generateDummyApplications();

        StatusPieChart.setAnimated(false);
        StatusPieChart.setLegendVisible(true);
        StatusPieChart.setLabelsVisible(false);
    }


    public void setUserData(User user) {
        if (user instanceof Auditor) {
            this.loggedInAuditor = (Auditor) user;
            LoadChartOA(null);
        }
    }

    @FXML
    public void LoadChartOA(ActionEvent actionEvent) {
        if (loggedInAuditor == null) return;

        String filterType = "All Time"; // Default
        if (ThisYearRB.isSelected()) filterType = "This Year";
        else if (ThisMonthRB.isSelected()) filterType = "This Month";

        Map<String, Integer> stats = loggedInAuditor.getApplicationStats(filterType);


        chartData.clear(); // Clear old data first

        int totalCount = 0;

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            int count = entry.getValue();
            totalCount += count;

            if (count > 0) {
                chartData.add(new PieChart.Data(entry.getKey() + " (" + count + ")", count));
            }
        }

        StatusPieChart.setTitle("Application Status (" + filterType + ")");

        if (totalCount == 0) {
            SummaryText.setText("No applications found for the period: " + filterType);
        } else {
            SummaryText.setText("Total Applications Analyzed: " + totalCount);
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/AuditorDashboardView.fxml",
                actionEvent,
                (AuditorDashboardViewController controller) -> {
                    controller.setUserData(loggedInAuditor);
                }
        );
    }
}