package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;

public class GenerateAuditReportViewController {
    @javafx.fxml.FXML
    private DatePicker StartDateDP;
    @javafx.fxml.FXML
    private DatePicker EndDateDP;

    @javafx.fxml.FXML
    public void GenerateReportOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AllDashboards/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
