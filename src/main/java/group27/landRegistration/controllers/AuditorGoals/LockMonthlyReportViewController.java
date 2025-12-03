package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LockMonthlyReportViewController {
    @javafx.fxml.FXML
    private TableColumn MonthTC;
    @javafx.fxml.FXML
    private TableColumn StatusTC;
    @javafx.fxml.FXML
    private TableColumn CreatedDateTC;
    @javafx.fxml.FXML
    private TableColumn LastUpdatedTC;
    @javafx.fxml.FXML
    private TableView MonthlyReportTV;
    @javafx.fxml.FXML
    private TableColumn RegistrarTC;
    @javafx.fxml.FXML
    private ComboBox SelectMonthCB;

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

    @javafx.fxml.FXML
    public void ApproveLockOA(ActionEvent actionEvent) {
    }
}
