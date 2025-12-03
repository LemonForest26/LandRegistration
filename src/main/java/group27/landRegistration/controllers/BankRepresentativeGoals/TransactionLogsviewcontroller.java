package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class TransactionLogsviewcontroller {
    @javafx.fxml.FXML
    private TableColumn TransactionListTableColumn;
    @javafx.fxml.FXML
    private TableView TransactionListTableview;

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AllDashboards/BankRepresentativeDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
