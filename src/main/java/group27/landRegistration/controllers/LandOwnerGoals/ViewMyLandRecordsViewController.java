package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewMyLandRecordsViewController {

    @javafx.fxml.FXML
    private TableColumn PlotIDCol;
    @javafx.fxml.FXML
    private TableColumn AreaCol;
    @javafx.fxml.FXML
    private TableView RecordTableView;
    @javafx.fxml.FXML
    private TableColumn ZoningCol;

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void ViewRecordOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
