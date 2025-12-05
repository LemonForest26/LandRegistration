package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.users.LandRegistrar;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;

public class ApproveRejectApplicationViewController {

    @javafx.fxml.FXML
    private TextField PlotIDTF;
    @javafx.fxml.FXML
    private TextArea RemarksTF;

    private LandRegistrar landReg;
    @javafx.fxml.FXML
    private TextField ApplicationIDTF;

    public void setUserData(User user) {
        if (user instanceof LandRegistrar) {
            this.landReg = (LandRegistrar) user;
        }
    }

    public User getLoggedInUser(){
        return landReg;
    }

    // ---------------------- REJECT ----------------------
    @javafx.fxml.FXML
    public void RejectOA(ActionEvent actionEvent) {
        try {
            Integer plotID = parseIntSafe(PlotIDTF.getText());
            Integer applicationID = parseIntSafe(ApplicationIDTF.getText());
            String remarks = RemarksTF.getText().trim();


            if (plotID == null || applicationID == null || remarks.isEmpty()) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid Input",
                        "All fields required", "Plot ID, Applicant ID, and Remarks are required.");
                return;
            }

            // Find Application
            Application app = findApplication(plotID, applicationID);
            if (app == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Not Found",
                        "Application Not Found", "No matching application.");
                return;
            }

            // Update status
            app.updateStatus("Rejected");
            app.addNote("Registrar Remarks: " + remarks);

            saveUpdatedApplication(app);

            CustomAlert.show(Alert.AlertType.INFORMATION, "Application Rejected",
                    "Status Updated", "Application ID: " + app.getApplicationID());

            BackOA(actionEvent);
        }
        catch(NumberFormatException e) {
            CustomAlert.show(
                    Alert.AlertType.ERROR,
                    "Invalid Input",
                    "Number Format Error",
                    "Please enter valid numeric values for Plot ID and Application ID."
            );
        }
        catch (Exception e) {
            CustomAlert.show(
                    Alert.AlertType.ERROR,
                    "Unexpected Error",
                    "An Error Occurred",
                    e.getMessage()
            );
        }


    }

    // ---------------------- APPROVE ----------------------
    @javafx.fxml.FXML
    public void ApproveOA(ActionEvent actionEvent) {
        try {
            Integer plotID = parseIntSafe(PlotIDTF.getText());
            Integer applicationID = parseIntSafe(ApplicationIDTF.getText());
            String remarks = RemarksTF.getText().trim();


            if (plotID == null || applicationID == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Invalid Input",
                        "Plot ID and Applicant ID required", "Provide required fields.");
                return;
            }

            // Find Application
            Application app = findApplication(plotID, applicationID);
            if (app == null) {
                CustomAlert.show(Alert.AlertType.ERROR, "Not Found",
                        "Application Not Found", "No matching application.");
                return;
            }

            // Update status
            app.updateStatus("Approved");
            if (!remarks.isEmpty()) app.addNote("Registrar Remarks: " + remarks);

            saveUpdatedApplication(app);

            CustomAlert.show(Alert.AlertType.INFORMATION, "Application Approved",
                    "Status Updated", "Application ID: " + app.getApplicationID());

            BackOA(actionEvent);
        }
        catch(NumberFormatException e) {
            CustomAlert.show(
                    Alert.AlertType.ERROR,
                    "Invalid Input",
                    "Number Format Error",
                    "Please enter valid numeric values for Plot ID and Application ID."
            );
        }
        catch (Exception e) {
            CustomAlert.show(
                    Alert.AlertType.ERROR,
                    "Unexpected Error",
                    "An Error Occurred",
                    e.getMessage()
            );
        }
    }

    // ---------------------- HELPERS ----------------------
    private Integer parseIntSafe(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Application findApplication(int plotID, int applicationID) {
        FileManager<Application> appFM = new FileManager<>("Application.dat");
        List<Application> apps = appFM.loadList();

        for (Application a : apps) {
            if (a.getPlotID() == plotID && a.getApplicationID() == applicationID) {
                return a;
            }
        }
        return null;
    }

    private void saveUpdatedApplication(Application updatedApp) {
        // Update applications file
        FileManager<Application> appFM = new FileManager<>("Application.dat");
        List<Application> apps = appFM.loadList();

        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getApplicationID() == updatedApp.getApplicationID()) {
                apps.set(i, updatedApp);
                break;
            }
        }
        appFM.saveList(apps);

        // ALSO update inside plot object
        FileManager<Plot> plotFM = new FileManager<>("Plot.dat");
        List<Plot> plots = plotFM.loadList();

        for (Plot p : plots) {
            if (p.getPlotID() == updatedApp.getPlotID()) {
                p.updateApplication(updatedApp);
                break;
            }
        }
        plotFM.saveList(plots);
    }

    // ---------------------- BACK ----------------------
    @javafx.fxml.FXML
    public void BackOA(ActionEvent actionEvent) {
//        backToDashboard(actionEvent);
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
                actionEvent,
                (LandRegistrarDashBoardViewController controller) -> {
                    controller.setUserData(landReg);
                }
        );

    }

//    private void backToDashboard(ActionEvent actionEvent) {
//        try {
//            CurrentPageLoader page = new CurrentPageLoader();
//            page.loadWithData(
//                    "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
//                    actionEvent,
//                    controller -> {
//                        try {
//                            controller.getClass()
//                                    .getMethod("setUserData", User.class)
//                                    .invoke(controller, loggedInUser);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
