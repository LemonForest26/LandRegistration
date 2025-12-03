package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class QuickMeasurementViewController {
    @javafx.fxml.FXML
    private TextField plotiDTextfield;
    @javafx.fxml.FXML
    private TextField MeasurementtextField;

    @javafx.fxml.FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void SaveMeasurementOnAction(ActionEvent actionEvent) {
    }
}
