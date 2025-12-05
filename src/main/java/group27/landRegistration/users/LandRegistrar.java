package group27.landRegistration.users;

import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.nonUsers.Dispute;
import group27.landRegistration.nonUsers.OfficerLog;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.utility.FileManager;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LandRegistrar extends User {

    public LandRegistrar(String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, gender, NID, phoneNumber, doB);
    }

    // --- GOAL: REVIEW & PROCESS APPLICATIONS ---
    public void processApplication(Application app, String newStatus, String remarks) {
        // 1. Update the Application Object
        app.setStatus(newStatus);
        if (remarks != null && !remarks.isEmpty()) {
            app.addNote("Registrar: " + remarks);
        }
        app.setDateUpdated(LocalDate.now());

        // 2. Update Application.dat
        FileManager<Application> appFM = new FileManager<>("Application.dat");
        List<Application> apps = appFM.loadList();

        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getApplicationID() == app.getApplicationID()) {
                apps.set(i, app);
                break;
            }
        }
        appFM.saveList(apps);

        // 3. Update Plot.dat (Sync the change inside the Plot object)
        FileManager<Plot> plotFM = new FileManager<>("Plot.dat");
        List<Plot> plots = plotFM.loadList();

        for (Plot p : plots) {
            if (p.getPlotID() == app.getPlotID()) {
                p.updateApplication(app); // Assumes Plot has this method
                break;
            }
        }
        plotFM.saveList(plots);
    }

    // --- GOAL: UPDATE ZONING ---
    public boolean updatePlotZoning(int plotID, String newZoning, String documentReference) {
        FileManager<Plot> fm = new FileManager<>("Plot.dat");
        List<Plot> plots = fm.loadList();

        boolean found = false;
        for (int i = 0; i < plots.size(); i++) {
            Plot p = plots.get(i);
            if (p.getPlotID() == plotID) {
                p.setZoning(newZoning);

                // Add log entry
                String log = "Zoning changed to " + newZoning + " by Registrar (" + this.getName() + "). Ref: " + documentReference;
                p.addSurveyLog(log);

                plots.set(i, p);
                found = true;
                break;
            }
        }

        if (found) {
            fm.saveList(plots);
        }
        return found;
    }

    // --- GOAL: OFFICER PERFORMANCE (FLAGGING) ---
    public void flagOfficer(int officerID, String reason) {
        OfficerLog log = new OfficerLog(officerID, "FLAGGED: " + reason, true);
        FileManager<OfficerLog> fm = new FileManager<>("OfficerLog.dat");
        fm.appendItem(log);
    }

    // --- GOAL: OFFICER PERFORMANCE (VIEWING) ---
    public List<OfficerLog> viewOfficerPerformance(int officerID) {
        FileManager<OfficerLog> fm = new FileManager<>("OfficerLog.dat");
        List<OfficerLog> allLogs = fm.loadList();

        return allLogs.stream()
                .filter(log -> log.getOfficerID() == officerID)
                .collect(Collectors.toList());
    }

    // --- GOAL: DISPUTE ASSIGNMENT ---
    public void assignSurveyorToDispute(int disputeID, int surveyorID, String note) {
        Dispute dispute = new Dispute(disputeID, surveyorID, this.getUserID(), note);
        FileManager<Dispute> fm = new FileManager<>("Dispute.dat");
        fm.appendItem(dispute);
    }

    // --- GOAL: FINALISE TRANSFER ---
    public void finiliseTransfer(int plotID, long oldOwnerID, long newOwnerID) {
        FileManager<Plot> fm = new FileManager<>("Plot.dat");
        List<Plot> plots = fm.loadList();
        boolean found = false;

        for (Plot p : plots) {
            if (p.getPlotID() == plotID) {
                // Update owner
                p.setOwnerID((int) newOwnerID); // Casting based on Plot class definition
                p.addSurveyLog("Ownership transferred from " + oldOwnerID + " to " + newOwnerID + " by Registrar.");
                found = true;
                break;
            }
        }

        if (found) {
            fm.saveList(plots);
        }
    }

    // Placeholders for methods that might be handled directly by controllers
    public void reviewPendingApplilcation() {
        // Logic handled in ReviewPendingApplicationViewController via FileManager
    }

    public void applicatinApproval() {
        // Logic handled in ApproveRejectApplicationViewController
    }
}