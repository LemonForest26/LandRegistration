package group27.landRegistration.nonUsers;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SystemActivityLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int IDCounter = 1000;
    static {
        try {
            File file = new File("SystemActivity.dat");
            if (file.exists() && file.length() > 0) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<SystemActivityLog> logs = (List<SystemActivityLog>) ois.readObject();
                ois.close();

                if (!logs.isEmpty()) {
                    int lastID = logs.get(logs.size() - 1).getLogID();
                    IDCounter = lastID;
                }
            }
        } catch (Exception ignored) {
            // If failed → keep default 1000
        }
    }

    private int logID;
    private LocalDateTime timestamp;
    private int userID;
    private String action;
    private boolean isFlagged;

    public SystemActivityLog(int logID, int userID, String action) {
        IDCounter++;
        this.logID = IDCounter;
        this.timestamp = LocalDateTime.now();
        this.userID = userID;
        this.action = action;
        this.isFlagged = false;
    }

    // Constructor for dummy data generation with specific time
    public SystemActivityLog(int logID, int userID, String action, LocalDateTime timestamp) {
        this.logID = logID;
        this.timestamp = timestamp;
        this.userID = userID;
        this.action = action;
        this.isFlagged = false;
    }

    public int getLogID() { return logID; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getUserID() { return userID; }
    public String getAction() { return action; }
    public boolean isFlagged() { return isFlagged; }
    public void setFlagged(boolean flagged) { isFlagged = flagged; }

    // Helper for TableView display
    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // --- STATIC BLOCK: GENERATE DUMMY DATA FOR TESTING ---
    static {
        File file = new File("SystemActivity.dat");
        if (!file.exists() || file.length() == 0) {
            List<SystemActivityLog> dummies = new ArrayList<>();
            int id = 100;

            dummies.add(new SystemActivityLog(id++, 1002, "Logged In", LocalDateTime.now().minusHours(5)));
            dummies.add(new SystemActivityLog(id++, 1002, "Submitted Application #11", LocalDateTime.now().minusHours(4)));
            dummies.add(new SystemActivityLog(id++, 1003, "Admin Login", LocalDateTime.now().minusHours(3)));
            dummies.add(new SystemActivityLog(id++, 1003, "Deleted User #1001", LocalDateTime.now().minusHours(3).minusMinutes(10)));
            dummies.add(new SystemActivityLog(id++, 1005, "Attempted unauthorized access", LocalDateTime.now().minusHours(1)));

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(dummies);
            } catch (Exception ignored) {}
        }
    }
}