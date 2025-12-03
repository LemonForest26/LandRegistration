package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UpdateSurveyRecordViewController {
    @javafx.fxml.FXML
    private TextArea NotesTextArea;
    @javafx.fxml.FXML
    private TextField SurveyIDTextField;

    @javafx.fxml.FXML
    public void UpdateRecordOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
