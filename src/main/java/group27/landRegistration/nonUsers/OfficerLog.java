package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class OfficerLog implements Serializable {
    private static final long serialVersionUID = 1L;

    // Static ID counter logic (optional, but good for tracking specific log entries)
    private static int idCounter = 1;
    static {
        try {
            File file = new File("OfficerLog.dat");
            if (file.exists() && file.length() > 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<OfficerLog> logs = (List<OfficerLog>) ois.readObject();
                ois.close();
                if (!logs.isEmpty()) {
                    idCounter = logs.get(logs.size() - 1).getLogID() + 1;
                }
            }
        } catch (Exception ignored) { }
    }

    private int logID;
    private int officerID;
    private LocalDate date;
    private String actionDescription;
    private boolean isFlagged; // true = negative flag, false = neutral/positive log

    public OfficerLog(int officerID, String actionDescription, boolean isFlagged) {
        this.logID = idCounter++;
        this.officerID = officerID;
        this.date = LocalDate.now();
        this.actionDescription = actionDescription;
        this.isFlagged = isFlagged;
    }

    public int getLogID() { return logID; }
    public int getOfficerID() { return officerID; }
    public LocalDate getDate() { return date; }
    public String getActionDescription() { return actionDescription; }
    public boolean isIsFlagged() { return isFlagged; } // JavaFX naming convention

    @Override
    public String toString() {
        return date + ": " + actionDescription;
    }
}