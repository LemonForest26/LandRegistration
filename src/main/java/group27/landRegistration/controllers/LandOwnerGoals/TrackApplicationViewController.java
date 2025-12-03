package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TrackApplicationViewController {
    @javafx.fxml.FXML
    private Label StatusResultL;
    @javafx.fxml.FXML
    private TextField TrackingIDTF;

    @javafx.fxml.FXML
    public void TrackOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
