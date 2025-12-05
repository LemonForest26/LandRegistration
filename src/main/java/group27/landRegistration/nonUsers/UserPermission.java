package group27.landRegistration.nonUsers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserPermission implements Serializable {
    private static final long serialVersionUID = 1L;

    private int permissionID;
    private int userID;
    private String module;      // e.g., "Application Processing"
    private String action;      // e.g., "Approve"
    private String accessLevel; // "High", "Medium", "Read-Only"
    private boolean isFlaggedForReview;

    public UserPermission(int permissionID, int userID, String module, String action, String accessLevel) {
        this.permissionID = permissionID;
        this.userID = userID;
        this.module = module;
        this.action = action;
        this.accessLevel = accessLevel;
        this.isFlaggedForReview = false;
    }

    // Getters and Setters
    public int getPermissionID() { return permissionID; }
    public int getUserID() { return userID; }
    public String getModule() { return module; }
    public String getAction() { return action; }
    public String getAccessLevel() { return accessLevel; }

    public boolean isFlaggedForReview() { return isFlaggedForReview; }
    public void setFlaggedForReview(boolean flaggedForReview) { isFlaggedForReview = flaggedForReview; }

    // --- STATIC BLOCK: GENERATE DUMMY DATA ---
    static {
        File file = new File("UserPermissions.dat");
        if (!file.exists() || file.length() == 0) {
            List<UserPermission> dummies = new ArrayList<>();
            int pid = 1;

            // Permissions for a hypothetical Registrar (ID 1001)
            dummies.add(new UserPermission(pid++, 1001, "Application", "Approve/Reject", "High"));
            dummies.add(new UserPermission(pid++, 1001, "Land Records", "Modify Ownership", "Critical"));

            // Permissions for a Surveyor (ID 1002)
            dummies.add(new UserPermission(pid++, 1002, "Plot Map", "Update Coordinates", "Medium"));
            dummies.add(new UserPermission(pid++, 1002, "Dispute", "View Details", "Read-Only"));

            // Permissions for a Land Owner (ID 1003)
            dummies.add(new UserPermission(pid++, 1003, "My Properties", "View", "Low"));
            dummies.add(new UserPermission(pid++, 1003, "Payment", "Submit Fee", "Low"));

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(dummies);
            } catch (Exception ignored) {}
        }
    }
}