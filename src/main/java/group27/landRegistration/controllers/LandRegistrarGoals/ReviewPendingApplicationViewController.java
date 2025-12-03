package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReviewPendingApplicationViewController {
    @javafx.fxml.FXML
    private TableColumn ApplicantNameTC;
    @javafx.fxml.FXML
    private TableView PendingApplicationsTV;
    @javafx.fxml.FXML
    private TableColumn StatusTC;
    @javafx.fxml.FXML
    private TableColumn PlotIDTC;
    @javafx.fxml.FXML
    private TableColumn MessageTC;
    @javafx.fxml.FXML
    private ComboBox StatusFilterCB;
    @javafx.fxml.FXML
    private TableColumn ApplicationIDTC;
    @javafx.fxml.FXML
    private TableColumn ApplicantIDTC;

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void ViewDetailsOA(ActionEvent actionEvent) {
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

    @javafx.fxml.FXML
    public void FilterOA(ActionEvent actionEvent) {
    }
}
