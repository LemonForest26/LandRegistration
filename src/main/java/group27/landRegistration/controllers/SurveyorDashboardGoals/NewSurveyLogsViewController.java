package group27.landRegistration.controllers.SurveyorDashboardGoals;

import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class NewSurveyLogsViewController {

    @FXML
    private TableView<SurveyLogEntry> SurveyLogsTableView;
    @FXML
    private TableColumn<SurveyLogEntry, String> SurveyLogsTablecolumn;
    @FXML
    private TableColumn<SurveyLogEntry, Integer> SlNoTableColumn;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user; // store user for reuse
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    private void initialize() {
        // 1. Configure Columns
        SlNoTableColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSlNo()).asObject());
        SurveyLogsTablecolumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLogContent()));

        // 2. Load Data
        loadLogs();
    }

    private void loadLogs() {
        // Load all plots because survey logs are stored inside Plot objects
        FileManager<Plot> fm = new FileManager<>("Plot.dat");
        List<Plot> plots = fm.loadList();

        ObservableList<SurveyLogEntry> logEntries = FXCollections.observableArrayList();
        int counter = 1;

        // Iterate through all plots and extract their logs
        for (Plot p : plots) {
            if (p.getSurveyLogs() != null && !p.getSurveyLogs().isEmpty()) {
                for (String log : p.getSurveyLogs()) {
                    // Prepend Plot ID for context
                    String formattedLog = "[Plot ID: " + p.getPlotID() + "] " + log;
                    logEntries.add(new SurveyLogEntry(counter++, formattedLog));
                }
            }
        }

        SurveyLogsTableView.setItems(logEntries);
    }

    @FXML
    public void GoBackOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.loadWithData(
                    "/group27/landRegistration/AllDashboards/SurveyorDashboardView.fxml",
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

    // --- Inner Class for Table Data Model ---
    public static class SurveyLogEntry {
        private final int slNo;
        private final String logContent;

        public SurveyLogEntry(int slNo, String logContent) {
            this.slNo = slNo;
            this.logContent = logContent;
        }

        public int getSlNo() { return slNo; }
        public String getLogContent() { return logContent; }
    }
}