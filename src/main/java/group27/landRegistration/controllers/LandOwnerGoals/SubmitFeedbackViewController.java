package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SubmitFeedbackViewController {
    @javafx.fxml.FXML
    private TextField SubjectTF;
    @javafx.fxml.FXML
    private TextArea MessageTA;

    @javafx.fxml.FXML
    public void SubmitFeedbackOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
