package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SendPaymentNoticeviewcontroller {
    @javafx.fxml.FXML
    private ComboBox NotificationTypeComboBox;
    @javafx.fxml.FXML
    private TextField TransactionIDTextField;

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void SubmitOnAction(ActionEvent actionEvent) {
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
