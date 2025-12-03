package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

public class ValidatePaymentProofviewcontroller {
    @javafx.fxml.FXML
    private TextArea MessageTextArea;

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
