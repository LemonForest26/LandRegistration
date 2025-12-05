package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.PublicUser;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PublicSearchByPlotViewController {

    @FXML private TableView<Plot> PlotTV;
    @FXML private TableColumn<Plot, Integer> OwnerCol; // Showing Owner ID
    @FXML private TableColumn<Plot, Double> AreaCol;
    @FXML private TableColumn<Plot, String> LocationCol;
    @FXML private TableColumn<Plot, String> PriceCol; // Computed Column

    @FXML private TextField SearchPlotTF;    // Plot ID Input
    @FXML private TextField LocationNameTF;  // Text Input

    private PublicUser loggedInPublicUser;

    @FXML
    private void initialize() {
        // 1. Setup Table Columns
        OwnerCol.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
        AreaCol.setCellValueFactory(new PropertyValueFactory<>("area"));
        LocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Computed Price Column: (Area * 1150) just for display
        PriceCol.setCellValueFactory(cellData -> {
            double estimatedPrice = cellData.getValue().getArea() * 1150;
            return new SimpleStringProperty(String.format("%,.0f BDT", estimatedPrice));
        });

        loadData();
    }

    public void setUserData(User user) {
        if (user instanceof PublicUser) {
            this.loggedInPublicUser = (PublicUser) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInPublicUser;
    }

    // Helper method to trigger search
    private void loadData() {
        if (loggedInPublicUser == null) return; // Guard clause

        String plotIdInput = SearchPlotTF.getText().trim();
        String locInput = LocationNameTF.getText().trim();

        List<Plot> results = loggedInPublicUser.searchPlots(plotIdInput, locInput);
        PlotTV.setItems(FXCollections.observableArrayList(results));
    }

    @FXML
    public void SearchPlotOA(ActionEvent actionEvent) {
        // Trigger search based on Text Fields
        loadData();
    }

    @FXML
    public void FilterOA(ActionEvent actionEvent) {
        // Trigger search based on ComboBoxes
        loadData();
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Clean navigation
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml",
                actionEvent,
                (PublicUserDashBoardViewController controller) -> {
                    controller.setUserData(loggedInPublicUser);
                }
        );
    }
}