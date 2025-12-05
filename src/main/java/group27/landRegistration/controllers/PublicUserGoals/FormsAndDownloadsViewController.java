package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import group27.landRegistration.users.PublicUser;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FormsAndDownloadsViewController {

    @FXML private ListView<String> FormListView;

    private PublicUser loggedInPublicUser;

    @FXML
    public void initialize() {
        // We can populate the list immediately since the form names are static/known
        // However, since we put the logic in the model, we need an instance of PublicUser.
        // But initialize() runs BEFORE setUserData().
        // Strategy: We will load the list in setUserData, OR create a temp dummy user just to get keys,
        // OR simply hardcode the keys here if they rarely change.

        // Let's populate hardcoded names for UI speed, matching the keys in PublicUser
        FormListView.setItems(FXCollections.observableArrayList(
                "Land Registration Application",
                "Mutation Request Form",
                "Tax Assessment Appeal"
        ));
    }

    public void setUserData(User user) {
        if (user instanceof PublicUser) {
            this.loggedInPublicUser = (PublicUser) user;
        }
    }

    public User getLoggedInUser(){
        return loggedInPublicUser;
    }

    @FXML
    public void DownloadFormOA(ActionEvent actionEvent) {
        String selectedForm = FormListView.getSelectionModel().getSelectedItem();

        if (selectedForm == null) {
            CustomAlert.show(Alert.AlertType.WARNING, "No Selection", "Warning",
                    "Please select a form from the list to download.");
            return;
        }

        // 1. Setup File Chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Form As");
        // Clean up the name for the file (remove spaces)
        String safeName = selectedForm.replaceAll(" ", "_") + ".txt";
        fileChooser.setInitialFileName(safeName);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // 2. Open Dialog
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        File destFile = fileChooser.showSaveDialog(stage);

        if (destFile != null) {
            try {
                // 3. Delegate to Model
                loggedInPublicUser.downloadForm(selectedForm, destFile);

                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Download Complete",
                        "Form saved successfully to: " + destFile.getAbsolutePath());

            } catch (IOException e) {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Download Failed",
                        "Could not save the file: " + e.getMessage());
            }
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        // Type-safe navigation
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml",
                actionEvent,
                (PublicUserDashBoardViewController controller) -> {
                    controller.setUserData(loggedInPublicUser);
                }
        );
    }
}