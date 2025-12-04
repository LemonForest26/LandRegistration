package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ApprovalSamplingViewController {
    @javafx.fxml.FXML
    private ComboBox RegistrarNameCB;
    @javafx.fxml.FXML
    private TableColumn StatusTC;
    @javafx.fxml.FXML
    private TableView ApprovalSamplingTV;
    @javafx.fxml.FXML
    private TableColumn PlotIDTC;
    @javafx.fxml.FXML
    private TableColumn ApprovedDateTC;
    @javafx.fxml.FXML
    private DatePicker StartDateDP;
    @javafx.fxml.FXML
    private DatePicker EndDateDP;
    @javafx.fxml.FXML
    private TableColumn ApplicationNameTC;
    @javafx.fxml.FXML
    private TableColumn ApplicationIDTC;

    private User loggedInUser;

    private void initialize(){

    }

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    public void FilterRegistrarOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void DateFilterOA(ActionEvent actionEvent) {
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
    public void FlagSampleOA(ActionEvent actionEvent) {
    }
}
