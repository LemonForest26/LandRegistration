package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class NoticesViewController {

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    private TextArea NoticeDetailTA;
    @javafx.fxml.FXML
    private ListView NoticeListView;

    @javafx.fxml.FXML
    public void AuctionsOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void AnnouncementsOA(ActionEvent actionEvent) {
    }


    @javafx.fxml.FXML
    public void ZoningUpdatesOA(ActionEvent actionEvent) {
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
