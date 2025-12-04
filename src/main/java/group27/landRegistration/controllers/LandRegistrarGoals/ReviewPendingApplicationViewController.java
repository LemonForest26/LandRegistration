package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.FileManager;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReviewPendingApplicationViewController {

    @FXML
    private TableView<Application> PendingApplicationsTV;
    @FXML
    private TableColumn<Application, Number> ApplicationIDTC;
    @FXML
    private TableColumn<Application, Number> PlotIDTC;
    @FXML
    private TableColumn<Application, Number> ApplicantIDTC;
    @FXML
    private TableColumn<Application, String> ApplicantNameTC;
    @FXML
    private TableColumn<Application, String> StatusTC;
    @FXML
    private TableColumn<Application, String> MessageTC;
    @FXML
    private ComboBox<String> StatusFilterCB;

    private User loggedInUser;
    private final ObservableList<Application> masterList = FXCollections.observableArrayList();
    private final Map<Integer, String> applicantNameCache = new HashMap<>();

    public void initialize() {
        ApplicationIDTC.setCellValueFactory(new PropertyValueFactory<>("applicationID"));
        PlotIDTC.setCellValueFactory(new PropertyValueFactory<>("plotID"));
        ApplicantIDTC.setCellValueFactory(new PropertyValueFactory<>("applicantID"));
        StatusTC.setCellValueFactory(new PropertyValueFactory<>("status"));
        MessageTC.setCellValueFactory(new PropertyValueFactory<>("notes"));

        ApplicantNameTC.setCellValueFactory(cellData -> {
            int applicantID = cellData.getValue().getApplicantID();
            String name = applicantNameCache.getOrDefault(applicantID, "Unknown User");
            return new SimpleStringProperty(name);
        });

        StatusFilterCB.setItems(FXCollections.observableArrayList("All", "Pending", "Approved", "Rejected"));
        StatusFilterCB.setValue("All");

        loadUserCache();
        loadApplicationData();
    }

    private void loadUserCache() {
        FileManager<User> userManager = new FileManager<>("users.dat");
        List<User> users = userManager.loadList();
        for (User u : users) {
            applicantNameCache.put(u.getUserID(), u.getName());
        }
    }

    private void loadApplicationData() {
        FileManager<Application> appManager = new FileManager<>("Application.dat");
        List<Application> apps = appManager.loadList();
        masterList.setAll(apps);
        PendingApplicationsTV.setItems(masterList);
    }

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    @FXML
    public void FilterOA(ActionEvent actionEvent) {
        String filterType = StatusFilterCB.getValue();

        if (filterType == null || filterType.equals("All")) {
            PendingApplicationsTV.setItems(masterList);
        } else {
            // Filter using Stream API
            List<Application> filtered = masterList.stream()
                    .filter(app -> app.getStatus().equalsIgnoreCase(filterType))
                    .collect(Collectors.toList());
            PendingApplicationsTV.setItems(FXCollections.observableArrayList(filtered));
        }
    }


    @FXML
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