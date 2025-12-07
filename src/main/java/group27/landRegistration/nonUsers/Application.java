package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int IDCounter = 1000;

    static {
        try {
            File file = new File("Application.dat");
            if (file.exists() && file.length() > 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<Application> apps = (List<Application>) ois.readObject();
                ois.close();
                if (!apps.isEmpty()) {
                    IDCounter = apps.get(apps.size() - 1).getApplicationID();
                }
            }
        } catch (Exception ignored) { }
    }

    private int applicationID, plotID, applicantID;
    private String status, notes;
    private LocalDate dateSubmitted, dateUpdated;
    private double loanAmount;
    private ArrayList<String> attachments = new ArrayList<>();

    // --- CONSTRUCTOR 1: For Mortgage Applications (Bank Representative Use) ---
    // This one requires the loanAmount.
    public Application(int plotID, int applicantID, String status, String notes, double loanAmount) {
        IDCounter++;
        this.applicationID = IDCounter;
        this.plotID = plotID;
        this.applicantID = applicantID;
        this.status = status;
        this.notes = notes;
        this.loanAmount = loanAmount;
        this.dateSubmitted = LocalDate.now();
        this.dateUpdated = LocalDate.now();
    }

    // --- CONSTRUCTOR 2: For General Applications (Land Owner Use) ---
    // This OVERLOADED constructor ensures other code doesn't break.
    // It sets loanAmount to 0.0 by default.
    public Application(int plotID, int applicantID, String status, String notes) {
        IDCounter++;
        this.applicationID = IDCounter;
        this.plotID = plotID;
        this.applicantID = applicantID;
        this.status = status;
        this.notes = notes;
        this.loanAmount = 0.0; // Default for non-mortgage apps
        this.dateSubmitted = LocalDate.now();
        this.dateUpdated = LocalDate.now();
    }

    // Getters and Setters
    public int getApplicationID() { return applicationID; }
    public int getPlotID() { return plotID; }
    public int getApplicantID() { return applicantID; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
        this.dateUpdated = LocalDate.now();
    }

    public double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getDateSubmitted() { return dateSubmitted; }
    public LocalDate getDateUpdated() { return dateUpdated; }

    public ArrayList<String> getAttachments() { return attachments; }

    // Helper to simulate getting a name
    public String getApplicantName() {
        return "Applicant #" + applicantID;
    }

    public void addAttachment(String file){
        attachments.add(file);
    }

    public void addNote(String note) {
        if (this.notes == null) this.notes = "";
        this.notes += "\n" + note;
    }

    @Override
    public String toString() {
        return "App ID: " + applicationID + " | Status: " + status;
    }

    // --- FIXED: Completed Setters ---
    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}