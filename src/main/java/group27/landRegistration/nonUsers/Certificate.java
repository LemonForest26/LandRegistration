package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Certificate implements Serializable {
    private static int IDCounter = 1000;
    static {
        try {
            File file = new File("Certificate.dat");
            if (file.exists() && file.length() > 0) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<Certificate> certs = (List<Certificate>) ois.readObject();
                ois.close();

                if (!certs.isEmpty()) {
                    int lastID = certs.get(certs.size() - 1).getCertificateID();
                    IDCounter = lastID;
                }
            }
        } catch (Exception ignored) {
            // If failed → keep default 1000
        }
    }

    private int ownerID, certificateID, applicationID;
    private String  certificationFilePath;
    private LocalDate issueDate;

    public Certificate(int ownerID, int applicationID, String certificationFilePath, LocalDate issueDate) {
        IDCounter++;
        this.certificateID = IDCounter;
        this.ownerID = ownerID;
        this.applicationID = applicationID;
        this.certificationFilePath = certificationFilePath;
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "ownerID=" + ownerID +
                ", certificateID=" + certificateID +
                ", applicationID=" + applicationID +
                ", certificationFilePath='" + certificationFilePath + '\'' +
                ", issueDate=" + issueDate +
                '}';
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

    public static int getIDCounter() {
        return IDCounter;
    }

    public static void setIDCounter(int IDCounter) {
        Certificate.IDCounter = IDCounter;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void generateCertificate(Application app, Plot plot) {

    }

    public String downloadCertificate() {
        return ""; //Might change!
    }
}
