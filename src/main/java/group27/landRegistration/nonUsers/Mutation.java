package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mutation implements Serializable {
    private static int IDCounter = 1000;
    static {
        try {
            File file = new File("Mutation.dat");
            if (file.exists() && file.length() > 0) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<Mutation> muts = (List<Mutation>) ois.readObject();
                ois.close();

                if (!muts.isEmpty()) {
                    int lastID = muts.get(muts.size() - 1).getMutationID();
                    IDCounter = lastID;
                }
            }
        } catch (Exception ignored) {
            // If failed → keep default 1000
        }
    }

    private int mutationID, plotID, oldOwnerID, newOwnerID;
    private String status, remarks;
    private LocalDate requestDate, approvalDate;

    public Mutation(int plotID, int oldOwnerID, int newOwnerID, String status, String remarks, LocalDate requestDate, LocalDate approvalDate) {
        IDCounter++;
        this.mutationID = IDCounter;
        this.plotID = plotID;
        this.oldOwnerID = oldOwnerID;
        this.newOwnerID = newOwnerID;
        this.status = status;
        this.remarks = remarks;
        this.requestDate = requestDate;
        this.approvalDate = approvalDate;
    }

    public Mutation(int plotID, int ownerID, String correctionText) {
        this.mutationID = IDCounter++;
        this.plotID = plotID;
        this.oldOwnerID = ownerID;
        this.newOwnerID = ownerID; // or -1
        this.status = "Correction Requested";
        this.remarks = correctionText;
        this.requestDate = LocalDate.now();
        this.approvalDate = null;
    }

    public static ArrayList<Mutation> mutationList = new ArrayList<>();

    public int getMutationID() {
        return mutationID;
    }

    public void setMutationID(int mutationID) {
        this.mutationID = mutationID;
    }

    public int getPlotID() {
        return plotID;
    }

    public void setPlotID(int plotID) {
        this.plotID = plotID;
    }

    public int getOldOwnerID() {
        return oldOwnerID;
    }

    public void setOldOwnerID(int oldOwnerID) {
        this.oldOwnerID = oldOwnerID;
    }

    public int getNewOwnerID() {
        return newOwnerID;
    }

    public void setNewOwnerID(int newOwnerID) {
        this.newOwnerID = newOwnerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public void approve(){

    }
    public void reject(String reason){

    }
}
