package group27.landRegistration.users;

import group27.landRegistration.nonUsers.Application;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.utility.FileManager;

import java.time.LocalDate;
import java.util.List;

public class LandRegistrar extends User{
    public LandRegistrar(String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, gender, NID, phoneNumber, doB);
    }

    public void reviewPendingApplilcation() {

    }

    public void finiliseTransfer(int plotID, long oldOwnerID, long newOwnerID) {
        //OwnerShip Transfer...
    }

    public void applicatinApproval() {

    }
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
}
