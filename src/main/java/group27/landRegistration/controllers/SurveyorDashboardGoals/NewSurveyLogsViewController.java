package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class NewSurveyLogsViewController {
    @javafx.fxml.FXML
    private TableView SurveyLogsTableView;
    @javafx.fxml.FXML
    private TableColumn SurveyLogsTablecolumn;
    @javafx.fxml.FXML
    private TableColumn SlNoTableColumn;

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AuditorGoals/AuditorDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
