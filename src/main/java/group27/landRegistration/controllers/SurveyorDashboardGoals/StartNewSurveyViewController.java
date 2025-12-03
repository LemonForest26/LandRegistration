package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class StartNewSurveyViewController {
    @javafx.fxml.FXML
    private DatePicker SurveyDatePicker;
    @javafx.fxml.FXML
    private TextField PlotIDTextField;
    @javafx.fxml.FXML
    private TextArea BounaryCoordinatesTextArea;

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
