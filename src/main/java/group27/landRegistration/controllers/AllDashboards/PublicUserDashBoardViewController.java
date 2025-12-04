package group27.landRegistration.controllers.AllDashboards;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class PublicUserDashBoardViewController {

    @javafx.fxml.FXML
    private Label UserNameLb;
    @javafx.fxml.FXML
    private Label UserIDLb;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
        UserNameLb.setText(user.getName());
        UserIDLb.setText(String.valueOf(user.getUserID()));
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @javafx.fxml.FXML
    public void NoticesOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/PublicUserGoals/NoticesView.fxml",
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
    public void FormsAndDownloadsOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/PublicUserGoals/FormsAndDownloadsView.fxml",
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
    public void SubmitRequestOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/PublicUserGoals/SubmitRequestView.fxml",
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
    public void TrackRequestOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/PublicUserGoals/TrackRequestView.fxml",
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
    public void VerifyCertificateOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/PublicUserGoals/VerifyCertificateAuthenticityView.fxml",
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
    public void LogOutOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LogInView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void SearchPlotOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/PublicUserGoals/PublicSearchByPlotView.fxml",
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
    public void BasicFeeChartOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/PublicUserGoals/BasicFeeChartView.fxml",
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
    public void EditProfileOA(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();

            page.loadWithData(
                    "/group27/landRegistration/PublicUserGoals/EditProfileView.fxml",
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
