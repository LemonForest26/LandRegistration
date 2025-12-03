package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;

public class SurveyorHistoryPieChartViewController {
    @javafx.fxml.FXML
    private PieChart SurveyorHistoryPiechart;

    @javafx.fxml.FXML
    public void SurveyorHistoryViewpieChartOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BacktoHomeOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
