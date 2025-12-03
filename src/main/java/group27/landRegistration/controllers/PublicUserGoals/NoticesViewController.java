package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class NoticesViewController {
    @javafx.fxml.FXML
    private TextArea NoticeDetailTA;
    @javafx.fxml.FXML
    private ListView NoticeListView;

    @javafx.fxml.FXML
    public void AuctionsOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void AnnouncementsOA(ActionEvent actionEvent) {
    }


    @javafx.fxml.FXML
    public void ZoningUpdatesOA(ActionEvent actionEvent) {
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
