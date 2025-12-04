package group27.landRegistration.controllers.AllDashboards;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class BankRepresentativeDashboardViewController
{
    @javafx.fxml.FXML
    private DatePicker DateofPaymentSlipDatePicker;
    @javafx.fxml.FXML
    private Label UserNameLb;
    @javafx.fxml.FXML
    private Label UserIDLb;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void TransactionLogsOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/BankRepresentativeGoals/TransactionLogs.fxml", actionEvent);
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void SendPaymentNoticeOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/BankRepresentativeGoals/SendPaymentNotice.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void BacktoHomePageOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LogInView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void ValiddatePaymentProofOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/BankRepresentativeGoals/ValiddatePaymentProof.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void RefundCorrectionOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/BankRepresentativeGoals/RefundCorrection.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void ApproveMortgageonAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/BankRepresentativeGoals/ApproveMortgage.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void VerifyPaymentonAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/BankRepresentativeGoals/VerifyPayment.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void ReconcileTransactionsOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/BankRepresentativeGoals/ReconcileTransactions.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void PaymentVerificationPieChartonAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/BankRepresentativeGoals/PaymentVerificationPieChartView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
