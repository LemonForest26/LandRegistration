package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PayTaxViewController {
    @javafx.fxml.FXML
    private ComboBox PaymentMethodCB;
    @javafx.fxml.FXML
    private TextField PlotIDTF;

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void PayTaxOA(ActionEvent actionEvent) {
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
