package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GenerateCertificateViewController {
    @javafx.fxml.FXML
    private TextField PlotIDTF;
    @javafx.fxml.FXML
    private TextArea CertificateFilePathTA;
    @javafx.fxml.FXML
    private TextField ApplicationIDTF;
    @javafx.fxml.FXML
    private TextField OwnerIDTF;
    @javafx.fxml.FXML
    private DatePicker IssueDateDP;

    @javafx.fxml.FXML
    public void GeneratepdfOA(ActionEvent actionEvent) {
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
