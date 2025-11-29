package group27.landRegistration.nonUsers;

import java.time.LocalDate;
import java.util.ArrayList;

public class Mutation {
    private static int IDCounter = 100000;

    private int mutationID, plotID, oldOwnerID, newOwnerID;
    private String status, remarks;
    private LocalDate requestDate, approvalDate;

    public Mutation(int plotID, int oldOwnerID, int newOwnerID, String status, String remarks, LocalDate requestDate, LocalDate approvalDate) {
        this.mutationID = IDCounter++;
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
