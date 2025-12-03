package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class VerifyPaymentviewcontroller {
    @javafx.fxml.FXML
    private TextField TransactionIDTextField;
    @javafx.fxml.FXML
    private Label PaymentStatusLabel;

    @Deprecated
    public void ConfromOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackToHomeOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/AllDashboards/BankRepresentativeDashboardView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void VerifyOnAction(ActionEvent actionEvent) {
    }
}
