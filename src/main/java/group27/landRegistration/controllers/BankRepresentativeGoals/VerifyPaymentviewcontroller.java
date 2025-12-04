package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class VerifyPaymentviewcontroller {

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    private TextField TransactionIDTextField;
    @javafx.fxml.FXML
    private Label PaymentStatusLabel;

    @javafx.fxml.FXML
    public void BackToHomeOnAction(ActionEvent actionEvent) {
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

    @javafx.fxml.FXML
    public void VerifyOnAction(ActionEvent actionEvent) {
    }
}
