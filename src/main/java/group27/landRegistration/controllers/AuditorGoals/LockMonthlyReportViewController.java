package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LockMonthlyReportViewController {
    @javafx.fxml.FXML
    private TableColumn MonthTC;
    @javafx.fxml.FXML
    private TableColumn StatusTC;
    @javafx.fxml.FXML
    private TableColumn CreatedDateTC;
    @javafx.fxml.FXML
    private TableColumn LastUpdatedTC;
    @javafx.fxml.FXML
    private TableView MonthlyReportTV;
    @javafx.fxml.FXML
    private TableColumn RegistrarTC;
    @javafx.fxml.FXML
    private ComboBox SelectMonthCB;

    private User loggedInUser;

    private void initialize() {

    }

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/AuditorDashboardView.fxml",
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void ApproveLockOA(ActionEvent actionEvent) {
    }
}
