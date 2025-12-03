package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class VerifyBoundaryViewController {
    @javafx.fxml.FXML
    private TextField MeasurementTextField;
    @javafx.fxml.FXML
    private TextField PlotIDTextField;

    @javafx.fxml.FXML
    public void SubmitOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
