package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.nonUsers.SystemActivityLog;
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

public class SurveyorHistoryPieChartViewController {

    @FXML
    private PieChart SurveyorHistoryPiechart;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void SurveyorHistoryViewpieChartOnAction(ActionEvent actionEvent) {
        // This button acts as a "Refresh" or "Load" button to generate the chart
        loadChartData();
    }

    // Helper method to load data
    private void loadChartData() {
        if (loggedInUser == null) return;

        // 1. Load Logs
        FileManager<SystemActivityLog> fm = new FileManager<>("SystemActivity.dat");
        List<SystemActivityLog> logs = fm.loadList();

        // 2. Categorize Activities
        Map<String, Integer> activityCounts = new HashMap<>();

        for (SystemActivityLog log : logs) {
            // Filter: Only process logs for THIS surveyor
            if (log.getUserID() == loggedInUser.getUserID()) {
                String action = log.getAction().toLowerCase();
                String category = "Other";

                if (action.contains("inspection")) {
                    category = "Inspections";
                } else if (action.contains("measurement") || action.contains("updated area")) {
                    category = "Measurements";
                } else if (action.contains("evidence") || action.contains("dispute")) {
                    category = "Disputes";
                } else if (action.contains("survey record")) {
                    category = "Record Updates";
                } else if (action.contains("login")) {
                    category = "Logins";
                }

                activityCounts.put(category, activityCounts.getOrDefault(category, 0) + 1);
            }
        }

        // 3. Populate Pie Chart
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : activityCounts.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey() + " (" + entry.getValue() + ")", entry.getValue()));
        }

        SurveyorHistoryPiechart.setData(pieData);
        SurveyorHistoryPiechart.setTitle("My Activity History");

        // Optional: Show legend
        SurveyorHistoryPiechart.setLegendVisible(true);
    }

    @FXML
    public void BacktoHomeOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml",
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}