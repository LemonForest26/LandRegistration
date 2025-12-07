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

    public void processApplication(Application app, String newStatus, String remarks) {
        // 1. Update the Application Object
        // setStatus automatically updates the 'dateUpdated' field in the Application class
        app.setStatus(newStatus);

        if (remarks != null && !remarks.isEmpty()) {
            app.addNote("Registrar: " + remarks);
        }

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

        // 3. Update Plot.dat (Sync changes & TRANSFER OWNERSHIP if approved)
        FileManager<Plot> plotFM = new FileManager<>("Plot.dat");
        List<Plot> plots = plotFM.loadList();

        for (int i = 0; i < plots.size(); i++) {
            Plot p = plots.get(i);

            if (p.getPlotID() == app.getPlotID()) {
                // Update the internal application list inside the plot
                p.updateApplication(app);

                // If Approved, the Applicant becomes the new Owner of the Plot
                if ("Approved".equalsIgnoreCase(newStatus)) {
                    // Log the transfer before changing the ID to keep a record of the old owner
                    p.addSurveyLog("Ownership transferred from ID " + p.getOwnerID() + " to ID " + app.getApplicantID() + " via Application Approval.");
                    p.setOwnerID(app.getApplicantID());
                }

                plots.set(i, p);
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
        // Requires the OfficerLog class (created below)
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
    public void finalizeTransfer(int plotID, long oldOwnerID, long newOwnerID) {
        FileManager<Plot> fm = new FileManager<>("Plot.dat");
        List<Plot> plots = fm.loadList();
        boolean found = false;

        for (int i = 0; i < plots.size(); i++) {
            Plot p = plots.get(i);
            if (p.getPlotID() == plotID) {
                // Update owner
                p.setOwnerID((int) newOwnerID);
                p.addSurveyLog("Ownership transferred from " + oldOwnerID + " to " + newOwnerID + " by Registrar.");

                plots.set(i, p); // Explicitly update list
                found = true;
                break;
            }
        }

        if (found) {
            fm.saveList(plots);
        }
    }

    public java.util.Map<String, Double> getZoningStatistics(boolean byArea) {
        FileManager<Plot> fm = new FileManager<>("Plot.dat");
        List<Plot> plots = fm.loadList();

        java.util.Map<String, Double> stats = new java.util.HashMap<>();

        for (Plot p : plots) {
            String zone = p.getZoning();
            if (zone == null || zone.isEmpty()) zone = "Unclassified";

            double valueToAdd = byArea ? p.getArea() : 1.0;

            stats.put(zone, stats.getOrDefault(zone, 0.0) + valueToAdd);
        }
        return stats;
    }
}