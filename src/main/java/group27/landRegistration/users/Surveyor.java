package group27.landRegistration.users;

import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.utility.FileManager;

import java.time.LocalDate;
import java.util.List;

public class Surveyor extends User {

    public Surveyor(String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, gender, NID, phoneNumber, doB);
    }

    public boolean recordMeasurement(int plotID, double newArea) {
        FileManager<Plot> plotManager = new FileManager<>("Plot.dat");
        List<Plot> plots = plotManager.loadList();

        boolean found = false;

        for (int i = 0; i < plots.size(); i++) {
            Plot p = plots.get(i);
            if (p.getPlotID() == plotID) {
                // 1. Update the Area
                p.setArea(newArea);

                // 2. Add a Log (Audit Trail)
                // Assumes Plot has: addSurveyLog(String log)
                String logEntry = "Area updated to " + newArea + " sq.ft by Surveyor ID: " + this.getUserID() + " on " + LocalDate.now();
                p.addSurveyLog(logEntry);

                // 3. Update the list
                plots.set(i, p);
                found = true;
                break;
            }
        }

        if (found) {
            plotManager.saveList(plots);
        }

        return found;
    }
}