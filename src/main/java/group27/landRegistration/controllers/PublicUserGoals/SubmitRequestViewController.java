package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SubmitRequestViewController {
    @javafx.fxml.FXML
    private TextArea MessageTA;
    @javafx.fxml.FXML
    private TextField NameTF;
    @javafx.fxml.FXML
    private ComboBox RequestTypeCB;

    @javafx.fxml.FXML
    public void SubmitRequestOA(ActionEvent actionEvent) {
    }


    private void initialize() {

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
