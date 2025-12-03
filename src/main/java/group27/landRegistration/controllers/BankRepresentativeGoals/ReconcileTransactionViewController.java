package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReconcileTransactionViewController {
    @javafx.fxml.FXML
    private TableColumn RegistryStatusTablecolumn;
    @javafx.fxml.FXML
    private TableView ReconciliationTableView;
    @javafx.fxml.FXML
    private TableColumn BankstatusTablecolumn;
    @javafx.fxml.FXML
    private TableColumn MatchTableColumn;

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void gobackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
