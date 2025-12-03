package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class ApproveMortgageviewcontroller {
    @javafx.fxml.FXML
    private Label LoanAmountTextField;
    @javafx.fxml.FXML
    private Label ApplicationNameTextfield;
    @javafx.fxml.FXML
    private Label plotIDtextfield;

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
