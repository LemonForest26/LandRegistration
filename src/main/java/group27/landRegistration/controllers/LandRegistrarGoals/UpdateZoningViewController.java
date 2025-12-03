package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UpdateZoningViewController {
    @javafx.fxml.FXML
    private TextField PlotIDTF;
    @javafx.fxml.FXML
    private TextArea FileUploadTA;
    @javafx.fxml.FXML
    private ComboBox NewZoningCB;

    private void initialize() {

    }


    @javafx.fxml.FXML
    public void SaveZoningOA(ActionEvent actionEvent) {
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
