package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SubmitNewRegistrationViewController {
    @javafx.fxml.FXML
    private TextField PlotIDTF;
    @javafx.fxml.FXML
    private TextField AddressTF;
    @javafx.fxml.FXML
    private TextArea DocumentsTextTA;

    @javafx.fxml.FXML
    public void SubmitRegistrationOA(ActionEvent actionEvent) {
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
