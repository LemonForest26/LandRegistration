package group27.landRegistration.nonUsers;

import group27.landRegistration.users.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plot implements Serializable {
    private static int idCounter = 1000;

    static {
        try {
            File file = new File("Plot.dat");
            if (file.exists() && file.length() > 0) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<Plot> plots = (List<Plot>) ois.readObject();
                ois.close();

                if (!plots.isEmpty()) {
                    int lastID = plots.get(plots.size() - 1).getPlotID();
                    idCounter = lastID;
                }
            }
        } catch (Exception ignored) {
            // If failed → keep default 1000
        }
    }
    private int plotID, ownerID;
    private String location, zoning;
    private double area;
    private ArrayList<Mutation> mutations;
    private ArrayList<Application> applications;

    public Plot(int ownerID, String location, String zoning, double area) {
        idCounter++;
        this.plotID = idCounter;
        this.ownerID = ownerID;
        this.location = location;
        this.zoning = zoning;
        this.area = area;
        this.applications = new ArrayList<>();
        this.mutations = new ArrayList<>();
    }

    public int getPlotID() {
        return plotID;
    }

    public void setPlotID(int plotID) {
        this.plotID = plotID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZoning() {
        return zoning;
    }

    public void setZoning(String zoning) {
        this.zoning = zoning;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public ArrayList<Mutation> getMutations() {
        return mutations;
    }

    public void setMutations(ArrayList<Mutation> mutations) {
        this.mutations = mutations;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Plot{" +
                "ownerID=" + ownerID +
                ", location='" + location + '\'' +
                ", zoning='" + zoning + '\'' +
                ", area=" + area +
                ", mutations=" + mutations +
                ", applications=" + applications +
                '}';
    }

    public void changeOwner(String newOwner) {

    }

    public void addMutation(Mutation m) {

    }

    public void addApplication(Application app) {
        if (applications == null) applications = new ArrayList<>();
        applications.add(app);
    }


    private ArrayList<String> surveyLogs = new ArrayList<>();

    public void addSurveyLog(String log) {
        if (surveyLogs == null) surveyLogs = new ArrayList<>();
        surveyLogs.add(log);
    }

    public ArrayList<String> getSurveyLogs() { return surveyLogs; }

    public void updateApplication(Application updated) {
        if (applications == null) return;
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).getApplicationID() == updated.getApplicationID()) {
                applications.set(i, updated);
                return;
            }
        }
    }

}
