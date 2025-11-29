package group27.landRegistration.nonUsers;

import java.util.ArrayList;

public class Plot {
    private int ownerID;
    private String location, zoning;
    private double area;
    private ArrayList<Mutation> mutations;
    private ArrayList<Application> applications;

    public Plot(int ownerID, String location, String zoning, double area) {
        this.ownerID = ownerID;
        this.location = location;
        this.zoning = zoning;
        this.area = area;
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

    }
}
