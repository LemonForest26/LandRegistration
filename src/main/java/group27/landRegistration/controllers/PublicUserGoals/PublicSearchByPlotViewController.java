package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PublicSearchByPlotViewController {
    @javafx.fxml.FXML
    private TableColumn PriceCol;
    @javafx.fxml.FXML
    private TableColumn OwnerCol;
    @javafx.fxml.FXML
    private TableView PlotTV;
    @javafx.fxml.FXML
    private TableColumn AreaCol;
    @javafx.fxml.FXML
    private TextField SearchPlotTF;
    @javafx.fxml.FXML
    private TableColumn LocationCol;
    @javafx.fxml.FXML
    private ComboBox DistrictCB;
    @javafx.fxml.FXML
    private ComboBox DivisionCB;
    @javafx.fxml.FXML
    private TextField LocationNameTF;


    private void initialize() {

    }

    @javafx.fxml.FXML
    public void SearchPlotOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void FilterOA(ActionEvent actionEvent) {
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
