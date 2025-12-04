package group27.landRegistration.controllers.BankRepresentativeGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class TransactionLogsviewcontroller {

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    private TableColumn TransactionListTableColumn;
    @javafx.fxml.FXML
    private TableView TransactionListTableview;

    private void initialize() {

    }

    @javafx.fxml.FXML
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
