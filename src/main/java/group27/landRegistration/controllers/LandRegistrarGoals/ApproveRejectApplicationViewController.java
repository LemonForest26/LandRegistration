package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ApproveRejectApplicationViewController {
    @javafx.fxml.FXML
    private TextField PlotIDTF;
    @javafx.fxml.FXML
    private TextArea RemarksTF;
    @javafx.fxml.FXML
    private TextField ApplicantIDTF;

    @javafx.fxml.FXML
    public void RejectOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void ApproveOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
