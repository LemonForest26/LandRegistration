package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BasicFeeChartViewController {
    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    private TableView FeeTV;
    @javafx.fxml.FXML
    private TableColumn RegistrationFeeCol;
    @javafx.fxml.FXML
    private TableColumn MutationFeeCol;
    @javafx.fxml.FXML
    private TableColumn TaxRateCol;


    private void initialize() {

    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml",
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
