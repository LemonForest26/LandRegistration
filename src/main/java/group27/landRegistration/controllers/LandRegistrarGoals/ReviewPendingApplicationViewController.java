package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReviewPendingApplicationViewController {
    @javafx.fxml.FXML
    private TableColumn ApplicantNameTC;
    @javafx.fxml.FXML
    private TableView PendingApplicationsTV;
    @javafx.fxml.FXML
    private TableColumn StatusTC;
    @javafx.fxml.FXML
    private TableColumn PlotIDTC;
    @javafx.fxml.FXML
    private TableColumn MessageTC;
    @javafx.fxml.FXML
    private ComboBox StatusFilterCB;
    @javafx.fxml.FXML
    private TableColumn ApplicationIDTC;
    @javafx.fxml.FXML
    private TableColumn ApplicantIDTC;

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
    public void ViewDetailsOA(ActionEvent actionEvent) {
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

    @javafx.fxml.FXML
    public void FilterOA(ActionEvent actionEvent) {
    }
}
