package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.users.User;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

public class BasicFeeChartViewController {

    @FXML private TableView<FeeStructure> FeeTV;
    @FXML private TableColumn<FeeStructure, String> RegistrationFeeCol;
    @FXML private TableColumn<FeeStructure, String> MutationFeeCol;
    @FXML private TableColumn<FeeStructure, String> TaxRateCol;
    // You might want to add a "Land Type" column in SceneBuilder later to distinguish rows

    private User loggedInUser;

    public void initialize() {
        // 1. Setup Columns
        RegistrationFeeCol.setCellValueFactory(new PropertyValueFactory<>("registrationFee"));
        MutationFeeCol.setCellValueFactory(new PropertyValueFactory<>("mutationFee"));
        TaxRateCol.setCellValueFactory(new PropertyValueFactory<>("taxRate"));

        // 2. Load Data (Static standard data for now)
        ObservableList<FeeStructure> feeList = FXCollections.observableArrayList(
                new FeeStructure("1% of Deed Value", "1,150 BDT (Fixed)", "3% (Rural)"),
                new FeeStructure("2% of Deed Value", "1,150 BDT (Fixed)", "4% (Municipal)"),
                new FeeStructure("2.5% of Deed Value", "5,000 BDT (Fixed)", "5% (Commercial)")
        );

        FeeTV.setItems(feeList);
    }

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Clean navigation using the Lambda approach
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml",
                actionEvent,
                (PublicUserDashBoardViewController controller) -> {
                    controller.setUserData(loggedInUser);
                }
        );
    }

    // --- Internal Class to represent the Table Data ---
    public static class FeeStructure {
        private final SimpleStringProperty registrationFee;
        private final SimpleStringProperty mutationFee;
        private final SimpleStringProperty taxRate;

        public FeeStructure(String regFee, String mutFee, String tax) {
            this.registrationFee = new SimpleStringProperty(regFee);
            this.mutationFee = new SimpleStringProperty(mutFee);
            this.taxRate = new SimpleStringProperty(tax);
        }

        public String getRegistrationFee() { return registrationFee.get(); }
        public String getMutationFee() { return mutationFee.get(); }
        public String getTaxRate() { return taxRate.get(); }
    }
}