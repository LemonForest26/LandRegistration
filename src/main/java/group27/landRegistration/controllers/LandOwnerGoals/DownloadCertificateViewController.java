package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DownloadCertificateViewController {
    @javafx.fxml.FXML
    private TableView CertificateTableView;
    @javafx.fxml.FXML
    private TableColumn CertificatesListCol;
    @javafx.fxml.FXML
    private TableColumn SLNoCol;

    private void initialize(){

    }


    @javafx.fxml.FXML
    public void DownloadCertificateOA(ActionEvent actionEvent) {
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
