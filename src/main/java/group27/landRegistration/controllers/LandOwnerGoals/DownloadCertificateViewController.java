package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.controllers.AllDashboards.LandOwnerDashBoardViewController;
import group27.landRegistration.nonUsers.Certificate;
import group27.landRegistration.users.LandOwner;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DownloadCertificateViewController {

    @FXML private TableView<Certificate> CertificateTableView;
    @FXML private TableColumn<Certificate, Integer> SLNoCol;
    @FXML private TableColumn<Certificate, String> CertificatesListCol;

    private LandOwner loggedInLandOwner;

    @FXML
    public void initialize() {
        SLNoCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCertificateID()));

        CertificatesListCol.setCellValueFactory(cellData -> {
            Certificate c = cellData.getValue();
            return new SimpleStringProperty("App ID: " + c.getApplicationID() + " | Issued: " + c.getIssueDate());
        });
    }

    public void setUserData(User user) {
        System.out.println("--- Set User Data Called ---"); // DEBUG 1

        if (user instanceof LandOwner) {
            this.loggedInLandOwner = (LandOwner) user;
            System.out.println("User is LandOwner. ID: " + loggedInLandOwner.getUserID()); // DEBUG 2

            // Fetch data
            List<Certificate> myCerts = loggedInLandOwner.viewMyCertificates();
            System.out.println("Certificates found for this user: " + myCerts.size()); // DEBUG 3

            CertificateTableView.setItems(FXCollections.observableArrayList(myCerts));
        } else {
            System.out.println("Error: User passed is NOT a LandOwner.");
        }
    }

    public User getLoggedInUser() {
        return loggedInLandOwner;
    }

    @FXML
    public void DownloadCertificateOA(ActionEvent actionEvent) {
        Certificate selectedCert = CertificateTableView.getSelectionModel().getSelectedItem();

        if (selectedCert == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Warning", "Please select a certificate to download.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Certificate");
        fileChooser.setInitialFileName("Certificate_" + selectedCert.getCertificateID() + ".txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        File destinationFile = fileChooser.showSaveDialog(stage);

        if (destinationFile != null) {
            try {
                loggedInLandOwner.downloadCertificateFile(selectedCert, destinationFile);
                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Download Complete",
                        "File saved to: " + destinationFile.getAbsolutePath());
            } catch (IOException e) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Download Failed", e.getMessage());
            }
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // FIXED: Using clean Lambda navigation
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(loggedInLandOwner);
                }
        );
    }
}