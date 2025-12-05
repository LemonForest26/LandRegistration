package group27.landRegistration.nonUsers;

import java.io.Serializable;
import java.time.LocalDate;

public class Dispute implements Serializable {
    private static final long serialVersionUID = 1L;

    private int disputeID; // Manually entered or reference ID
    private int assignedSurveyorID;
    private int assignedByRegistrarID;
    private String note;
    private String status;
    private LocalDate assignmentDate;

    public Dispute(int disputeID, int assignedSurveyorID, int assignedByRegistrarID, String note) {
        this.disputeID = disputeID;
        this.assignedSurveyorID = assignedSurveyorID;
        this.assignedByRegistrarID = assignedByRegistrarID;
        this.note = note;
        this.status = "Assigned";
        this.assignmentDate = LocalDate.now();
    }

    public int getDisputeID() { return disputeID; }
    public int getAssignedSurveyorID() { return assignedSurveyorID; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Dispute " + disputeID + " (Surveyor: " + assignedSurveyorID + ")";
    }
}