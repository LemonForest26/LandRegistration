package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.nonUsers.PaymentSlip;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class TransactionLogsviewcontroller {

    private User loggedInUser;

    @FXML
    private TableColumn<PaymentSlip, String> TransactionListTableColumn;
    @FXML
    private TableView<PaymentSlip> TransactionListTableview;

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    private void initialize() {
        // 1. Configure Column
        // Maps the PaymentSlip object to a single summary string for the table row
        TransactionListTableColumn.setCellValueFactory(data -> {
            PaymentSlip slip = data.getValue();
            String summary = String.format("Trans ID: %d | Plot: %d | Amount: %.2f | Status: %s | Date: %s",
                    slip.getTransactionID(),
                    slip.getPlotID(),
                    slip.getAmount(),
                    slip.getStatus(),
                    slip.getPaymentDate());
            return new SimpleStringProperty(summary);
        });

        // 2. Load Data
        loadData();
    }

    private void loadData() {
        FileManager<PaymentSlip> fm = new FileManager<>("PaymentSlip.dat");
        List<PaymentSlip> slips = fm.loadList();

        ObservableList<PaymentSlip> observableSlips = FXCollections.observableArrayList(slips);
        TransactionListTableview.setItems(observableSlips);
    }

    @FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/BankRepresentativeDashboardView.fxml",
                    actionEvent,
                    controller -> {
                        try {
                            controller.getClass()
                                    .getMethod("setUserData", User.class)
                                    .invoke(controller, loggedInUser);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}