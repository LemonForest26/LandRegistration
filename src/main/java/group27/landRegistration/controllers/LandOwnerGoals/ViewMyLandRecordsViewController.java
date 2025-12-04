package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.controllers.AllDashboards.LandOwnerDashBoardViewController;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.LandOwner;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewMyLandRecordsViewController {

    @FXML private TableView<Plot> RecordTableView;
    @FXML private TableColumn<Plot, Integer> PlotIDCol;
    @FXML private TableColumn<Plot, Double> AreaCol;
    @FXML private TableColumn<Plot, String> ZoningCol;

    private LandOwner loggedInLandOwner;

    @FXML
    private void initialize() {
        // Setup Columns
        PlotIDCol.setCellValueFactory(new PropertyValueFactory<>("plotID"));
        AreaCol.setCellValueFactory(new PropertyValueFactory<>("area"));
        ZoningCol.setCellValueFactory(new PropertyValueFactory<>("zoning"));
    }

    public void setUserData(User user) {
        if (user instanceof LandOwner) {
            this.loggedInLandOwner = (LandOwner) user;

            // DELEGATION: Ask the model to fetch the data
            RecordTableView.setItems(FXCollections.observableArrayList(
                    loggedInLandOwner.viewMyPlots()
            ));
        }
    }

    public User getLoggedInUser(){
        return loggedInLandOwner;
    }

    @FXML
    public void ViewRecordOA(ActionEvent actionEvent) {
        Plot selectedPlot = RecordTableView.getSelectionModel().getSelectedItem();

        if (selectedPlot == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Warning",
                    "Please select a plot from the table to view details.");
            return;
        }

        // Show detailed information in a pop-up
        String details = "Location: " + selectedPlot.getLocation() + "\n" +
                "Total Area: " + selectedPlot.getArea() + " sq.ft\n" +
                "Zoning Type: " + selectedPlot.getZoning() + "\n" +
                "Active Mutations: " + (selectedPlot.getMutations() != null ? selectedPlot.getMutations().size() : 0) + "\n" +
                "Pending Applications: " + (selectedPlot.getApplications() != null ? selectedPlot.getApplications().size() : 0);

        CustomAlert.show(Alert.AlertType.INFORMATION,
                "Plot Details",
                "Plot ID: " + selectedPlot.getPlotID(),
                details);
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Clean navigation
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(loggedInLandOwner);
                }
        );
    }
}