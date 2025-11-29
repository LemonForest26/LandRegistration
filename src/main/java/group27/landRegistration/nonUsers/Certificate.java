package group27.landRegistration.nonUsers;

import java.time.LocalDate;

public class Certificate {
    private static int IDCounter = 1000;

    private int ownerID, certificateID, applicationID;
    private String  certificationFilePath, ownerName;
    private LocalDate issueDate;

    public Certificate(int ownerID, int applicationID, String certificationFilePath, String ownerName, LocalDate issueDate) {
        this.certificateID = IDCounter++;
        this.ownerID = ownerID;
        this.applicationID = applicationID;
        this.certificationFilePath = certificationFilePath;
        this.ownerName = ownerName;
        this.issueDate = issueDate;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(int certificateID) {
        this.certificateID = certificateID;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public String getCertificationFilePath() {
        return certificationFilePath;
    }

    public void setCertificationFilePath(String certificationFilePath) {
        this.certificationFilePath = certificationFilePath;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "ownerID=" + ownerID +
                ", certificateID='" + certificateID + '\'' +
                ", applicationID='" + applicationID + '\'' +
                ", certificationFilePath='" + certificationFilePath + '\'' +
                ", issueDate=" + issueDate +
                '}';
    }

    public void generateCertificate(Application app, Plot plot) {

    }

    public String downloadCertificate() {
        return ""; //Might change!
    }
}
