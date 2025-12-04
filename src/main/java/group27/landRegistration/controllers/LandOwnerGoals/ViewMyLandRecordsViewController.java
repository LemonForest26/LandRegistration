package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewMyLandRecordsViewController {

    @javafx.fxml.FXML
    private TableColumn PlotIDCol;
    @javafx.fxml.FXML
    private TableColumn AreaCol;
    @javafx.fxml.FXML
    private TableView RecordTableView;
    @javafx.fxml.FXML
    private TableColumn ZoningCol;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    private void initialize() {

    }

    @javafx.fxml.FXML
    public void ViewRecordOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
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
