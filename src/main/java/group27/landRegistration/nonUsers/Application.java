package group27.landRegistration.nonUsers;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Application implements Serializable {
    private static int IDCounter = 1000;

    private int applicationID, plotID, applicantID;
    private String status, notes;
    private LocalDate dateSubmitted, dateUpdated;
    private ArrayList<String> attachments = new ArrayList<>();

    public Application(int plotID, int applicantID, String status, String notes, LocalDate dateSubmitted, LocalDate dateUpdated) {
        this.applicationID = IDCounter++;
        this.plotID = plotID;
        this.applicantID = applicantID;
        this.status = status;
        this.notes = notes;
        this.dateSubmitted = dateSubmitted;
        this.dateUpdated = dateUpdated;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public int getPlotID() {
        return plotID;
    }

    public void setPlotID(int plotID) {
        this.plotID = plotID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationID=" + applicationID +
                ", plotID=" + plotID +
                ", applicantID=" + applicantID +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", dateUpdated=" + dateUpdated +
                ", attachments=" + attachments +
                '}';
    }

    public void updateStatus(String newStatus){

    }
    public void addNote(String note){

    }
    public void addAttachment(String file){

    }
    public boolean isCompleted(){
       return false; //change later
    }
}

