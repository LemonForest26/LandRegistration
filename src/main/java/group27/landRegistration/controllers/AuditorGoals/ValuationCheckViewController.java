package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ValuationCheckViewController {

    @FXML private TextField PlotIDTF;
    @FXML private TableView<Plot> PlotDetailsTV;
    @FXML private TableColumn<Plot, Integer> UserTC; // Owner ID
    @FXML private TableColumn<Plot, Double> AreaTC;
    @FXML private TableColumn<Plot, String> CertifiedTC;
    @FXML private TableColumn<Plot, String> TimestampTC; // Using Zoning as proxy or Survey Log count

    private Auditor loggedInAuditor;
    private final ObservableList<Plot> masterList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        UserTC.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
        AreaTC.setCellValueFactory(new PropertyValueFactory<>("area"));

        TimestampTC.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getZoning()));

        CertifiedTC.setCellValueFactory(cellData -> {
            double area = cellData.getValue().getArea();
            double estimatedValue = area * 5000; // Example rate
            return new SimpleStringProperty(String.format("%,.2f BDT", estimatedValue));
        });

        PlotIDTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filterPlots(newValue);
        });
    }

    public void setUserData(User user) {
        if (user instanceof Auditor) {
            this.loggedInAuditor = (Auditor) user;
            loadData();
        }
    }

    public User getLoggedInUser(){
        return loggedInAuditor;
    }

    private void loadData() {
        if (loggedInAuditor == null) return;
        List<Plot> plots = loggedInAuditor.getAllPlots();
        masterList.setAll(plots);
        PlotDetailsTV.setItems(masterList);
    }

    private void filterPlots(String query) {
        if (query == null || query.isEmpty()) {
            PlotDetailsTV.setItems(masterList);
            return;
        }

        List<Plot> filtered = masterList.stream()
                .filter(p -> String.valueOf(p.getPlotID()).contains(query) ||
                        String.valueOf(p.getOwnerID()).contains(query))
                .collect(Collectors.toList());

        PlotDetailsTV.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    public void MarkForReviewOA(ActionEvent actionEvent) {
        Plot selectedPlot = PlotDetailsTV.getSelectionModel().getSelectedItem();

        if (selectedPlot == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Warning",
                    "Please select a plot to flag for valuation review.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Flag Valuation");
        dialog.setHeaderText("Flagging Plot ID: " + selectedPlot.getPlotID());
        dialog.setContentText("Reason for discrepancy:");

        dialog.showAndWait().ifPresent(reason -> {
            if (reason.trim().isEmpty()) return;

            loggedInAuditor.flagPlotValuation(selectedPlot, reason);

            CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Plot Flagged",
                    "The plot has been marked for further valuation assessment.");
        });
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/AuditorDashboardView.fxml",
                actionEvent,
                (AuditorDashboardViewController controller) -> {
                    controller.setUserData(loggedInAuditor);
                }
        );
    }
}