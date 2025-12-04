package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OfficerPerformanceViewController {
    @javafx.fxml.FXML
    private ComboBox OfficerTypeCB;
    @javafx.fxml.FXML
    private TableView PerformanceTV;
    @javafx.fxml.FXML
    private ComboBox OfficerNameCB;
    @javafx.fxml.FXML
    private TableColumn ActionsTC;
    @javafx.fxml.FXML
    private TableColumn DateTC;

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
    public void FlagOfficerOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
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
}
