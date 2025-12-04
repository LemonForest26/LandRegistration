package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Application implements Serializable {
    private static int IDCounter = 1000;

    static {
        try {
            File file = new File("Application.dat");
            if (file.exists() && file.length() > 0) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<Application> apps = (List<Application>) ois.readObject();
                ois.close();

                if (!apps.isEmpty()) {
                    int lastID = apps.get(apps.size() - 1).getApplicationID();
                    IDCounter = lastID;
                }
            }
        } catch (Exception ignored) {
            // If failed → keep default 1000
        }
    }

    private int applicationID, plotID, applicantID;
    private String status, notes;
    private LocalDate dateSubmitted, dateUpdated;
    private ArrayList<String> attachments = new ArrayList<>();

    public Application(int plotID, int applicantID, String status, String notes, LocalDate dateSubmitted, LocalDate dateUpdated) {
        IDCounter++;
        this.applicationID = IDCounter;
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


    public void addAttachment(String file){

    }
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        this.dateUpdated = LocalDate.now();
    }

    public void addNote(String note) {
        if (this.notes == null) this.notes = "";
        this.notes += "\n" + note;
    }

    public boolean isCompleted() {
        return "Approved".equalsIgnoreCase(status) || "Rejected".equalsIgnoreCase(status);
    }

}

