package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ValuationCheckViewController {
    @javafx.fxml.FXML
    private TextField PlotIDTF;
    @javafx.fxml.FXML
    private TableColumn UserTC;
    @javafx.fxml.FXML
    private TableColumn AreaTC;
    @javafx.fxml.FXML
    private TableView PlotDetailsTV;
    @javafx.fxml.FXML
    private TableColumn CertifiedTC;
    @javafx.fxml.FXML
    private TableColumn TimestampTC;

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
