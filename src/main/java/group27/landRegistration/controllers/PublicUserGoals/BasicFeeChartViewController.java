package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BasicFeeChartViewController {
    @javafx.fxml.FXML
    private TableView FeeTV;
    @javafx.fxml.FXML
    private TableColumn RegistrationFeeCol;
    @javafx.fxml.FXML
    private TableColumn MutationFeeCol;
    @javafx.fxml.FXML
    private TableColumn TaxRateCol;


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
