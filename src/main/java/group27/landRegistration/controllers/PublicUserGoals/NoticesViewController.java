package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import group27.landRegistration.nonUsers.Notice;
import group27.landRegistration.users.PublicUser;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.List;

public class NoticesViewController {

    @FXML private TextArea NoticeDetailTA;
    @FXML private ListView<Notice> NoticeListView;

    private PublicUser loggedInPublicUser;

    @FXML
    private void initialize() {
        // --- CRITICAL FIX: Force Notice class to load ---
        // This executes the static block in Notice.java to generate the dummy data file if it's missing.
        try {
            Class.forName("group27.landRegistration.nonUsers.Notice");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Add listener to show details when a notice is selected
        NoticeListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                NoticeDetailTA.setText(
                        "TITLE: " + newVal.getTitle() + "\n" +
                                "DATE: " + newVal.getDate() + "\n" +
                                "CATEGORY: " + newVal.getCategory() + "\n\n" +
                                "DETAILS:\n" + newVal.getContent()
                );
            }
        });
    }

    public void setUserData(User user) {
        if (user instanceof PublicUser) {
            this.loggedInPublicUser = (PublicUser) user;
            // Load default category when user data is set
            loadNotices("Announcement");
        }
    }

    public User getLoggedInUser(){
        return loggedInPublicUser;
    }

    private void loadNotices(String category) {
        if (loggedInPublicUser == null) return;

        List<Notice> notices = loggedInPublicUser.getNotices(category);
        NoticeListView.setItems(FXCollections.observableArrayList(notices));

        // Clear details when switching categories
        NoticeDetailTA.clear();
    }

    @FXML
    public void AnnouncementsOA(ActionEvent actionEvent) {
        loadNotices("Announcement");
    }

    @FXML
    public void AuctionsOA(ActionEvent actionEvent) {
        loadNotices("Auction");
    }

    @FXML
    public void ZoningUpdatesOA(ActionEvent actionEvent) {
        loadNotices("Zoning");
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml",
                actionEvent,
                (PublicUserDashBoardViewController controller) -> {
                    controller.setUserData(loggedInPublicUser);
                }
        );
    }
}