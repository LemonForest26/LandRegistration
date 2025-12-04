package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.nonUsers.Certificate;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
public class GenerateCertificateViewController {

    @FXML private TextField PlotIDTF;
    @FXML private TextArea CertificateFilePathTA;
    @FXML private TextField ApplicationIDTF;
    @FXML private TextField OwnerIDTF;
    @FXML private DatePicker IssueDateDP;

    private User loggedInUser;

    public void initialize() {
        // Set default date to today
        IssueDateDP.setValue(LocalDate.now());
        // Make the text area read-only as it displays the output path
        CertificateFilePathTA.setEditable(false);
    }

    public void setUserData(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    @FXML
    public void GeneratepdfOA(ActionEvent actionEvent) {
        // 1. Validation
        if (ApplicationIDTF.getText().isEmpty() || PlotIDTF.getText().isEmpty() ||
                OwnerIDTF.getText().isEmpty() || IssueDateDP.getValue() == null) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Missing Fields",
                    "Please fill in all ID fields and select a date.");
            return;
        }

        try {
            int appID = Integer.parseInt(ApplicationIDTF.getText());
            int plotID = Integer.parseInt(PlotIDTF.getText());
            int ownerID = Integer.parseInt(OwnerIDTF.getText());
            LocalDate issueDate = IssueDateDP.getValue();

            // 2. Cross-Verification (Application)
            FileManager<Application> appManager = new FileManager<>("Application.dat");
            Application app = appManager.find(a -> a.getApplicationID() == appID);

            if (app == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Data Error", "Application Not Found",
                        "No application found with ID: " + appID);
                return;
            }

            if (!"Approved".equalsIgnoreCase(app.getStatus())) {
                CustomAlert.show(Alert.AlertType.ERROR, "Protocol Error", "Application Pending/Rejected",
                        "You cannot generate a certificate for an application that is not 'Approved'.");
                return;
            }

            // 3. Cross-Verification (Plot & Owner)
            FileManager<Plot> plotManager = new FileManager<>("Plot.dat");
            Plot plot = plotManager.find(p -> p.getPlotID() == plotID);

            FileManager<User> userManager = new FileManager<>("users.dat");
            User owner = userManager.find(u -> u.getUserID() == ownerID);

            if (plot == null || owner == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Data Error", "Invalid IDs",
                        "The Plot ID or Owner ID provided does not exist in the database.");
                return;
            }

            // --- FILE CHOOSER LOGIC STARTS HERE ---

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Certificate");

            // Set default name and extension filter
            fileChooser.setInitialFileName("Certificate_" + appID + "_" + ownerID + ".txt");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            // Get the current stage (window) to show the dialog
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            File certFile = fileChooser.showSaveDialog(stage);

            // Only proceed if the user chose a file (didn't click cancel)
            if (certFile != null) {

                // Create object NOW to generate the ID
                Certificate newCert = new Certificate(ownerID, appID, certFile.getAbsolutePath(), issueDate);

                try (FileWriter writer = new FileWriter(certFile)) {
                    writer.write("========================================================================\n");
                    writer.write("           GOVERNMENT OF BANGLADESH LAND REGISTRY\n");
                    writer.write("========================================================================\n\n");
                    writer.write("CERTIFICATE OF LAND OWNERSHIP\n\n");

                    // Use the ID from the object
                    writer.write("Certificate ID: " + newCert.getCertificateID() + "\n");

                    writer.write("Date of Issue:  " + issueDate + "\n\n");
                    writer.write("This is to certify that the land described below is legally owned by:\n");
                    writer.write("Name: " + owner.getName() + "\n");
                    writer.write("NID:  " + owner.getNID() + "\n\n");
                    writer.write("PROPERTY DETAILS:\n");
                    writer.write("Plot ID:  " + plot.getPlotID() + "\n");
                    writer.write("Location: " + plot.getLocation() + "\n");
                    writer.write("Area:     " + plot.getArea() + " sq.ft\n\n");
                    writer.write("Authorized by: " + loggedInUser.getName() + " (Land Registrar)\n");
                    writer.write("========================================================================\n");
                }

                // Save to Database
                FileManager<Certificate> certManager = new FileManager<>("Certificate.dat");
                certManager.appendItem(newCert);

                // Update UI
                CertificateFilePathTA.setText(certFile.getAbsolutePath());
                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Certificate Saved",
                        "Certificate saved successfully to: " + certFile.getName());
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Numbers",
                    "Application ID, Plot ID, and Owner ID must be numeric.");
        } catch (IOException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "IO Error", "File System Error",
                    "Could not write the certificate file.");
            e.printStackTrace();
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