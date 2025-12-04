package group27.landRegistration.controllers.AllDashboards;

import group27.landRegistration.utility.CurrentPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class SurveyorDashboardViewController {
    @javafx.fxml.FXML
    private Label UserNameLb;
    @javafx.fxml.FXML
    private Label UserIDLb;

    @javafx.fxml.FXML
    public void DisputeEvidenceOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/SurveyorDashboardGoals/DisputeEvidence.fxml", actionEvent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void BackToHomepageOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/landRegistration/LogInView.fxml", actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void GenerateMapOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/SurveyorDashboardGoals/GenerateMap.fxml", actionEvent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void UpdateSurveyrecordOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/SurveyorDashboardGoals/UpdateSurveyrecord.fxml", actionEvent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void ViewSurveyLogsOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/SurveyorDashboardGoals/ViewSurveyLogs.fxml", actionEvent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void StartNewSurveyOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/SurveyorDashboardGoals/StartNewSurvey.fxml", actionEvent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void InspectionRequestsOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/SurveyorDashboardGoals/InspectionRequests.fxml", actionEvent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void VerifyBoundaryOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/SurveyorDashboardGoals/VerifyBoundary.fxml", actionEvent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void QuickMeasurementOnAction(ActionEvent actionEvent) {
        try {
            CurrentPageLoader page = new CurrentPageLoader();
            page.load("/group27/LandRegistration/SurveyorDashboardGoals/QuickMeasurement.fxml", actionEvent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
