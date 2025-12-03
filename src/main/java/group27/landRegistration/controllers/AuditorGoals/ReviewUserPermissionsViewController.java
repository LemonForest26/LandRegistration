package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReviewUserPermissionsViewController {
    @javafx.fxml.FXML
    private TableView PermissionsTV;
    @javafx.fxml.FXML
    private ComboBox SystemUsersCB;
    @javafx.fxml.FXML
    private TableColumn ActionTC;
    @javafx.fxml.FXML
    private TableColumn ModuleTC;
    @javafx.fxml.FXML
    private TableColumn AccessLevelTC;

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void MarkForReviewOA(ActionEvent actionEvent) {
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
