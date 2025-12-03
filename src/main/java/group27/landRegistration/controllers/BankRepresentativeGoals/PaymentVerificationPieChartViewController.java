package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;

public class PaymentVerificationPieChartViewController {
    @javafx.fxml.FXML
    private PieChart PaymentVerificationPieChart;

    @javafx.fxml.FXML
    public void BackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AllDashboards/BankRepresentativeDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void PaymentVerificationPieChartOnAction(ActionEvent actionEvent) {
    }
}
