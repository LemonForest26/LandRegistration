package group27.landRegistration.nonUsers;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MonthlyReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private int reportID;
    private String month; // e.g., "October"
    private int registrarID;
    private String status; // "Pending", "Reviewed", "Locked"
    private LocalDate createdDate;
    private LocalDate lastUpdated;

    public MonthlyReport(int reportID, String month, int registrarID, String status, LocalDate createdDate) {
        this.reportID = reportID;
        this.month = month;
        this.registrarID = registrarID;
        this.status = status;
        this.createdDate = createdDate;
        this.lastUpdated = LocalDate.now();
    }

    // Getters and Setters
    public int getReportID() { return reportID; }
    public String getMonth() { return month; }
    public int getRegistrarID() { return registrarID; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
        this.lastUpdated = LocalDate.now();
    }

    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getLastUpdated() { return lastUpdated; }

    @Override
    public String toString() {
        return month + " Report (" + status + ")";
    }

    // --- STATIC BLOCK: GENERATE DUMMY DATA ---
    static {
        File file = new File("MonthlyReport.dat");
        if (!file.exists() || file.length() == 0) {
            List<MonthlyReport> dummies = new ArrayList<>();
            int id = 500;

            dummies.add(new MonthlyReport(id++, "October", 1001, "Locked", LocalDate.now().minusMonths(2)));
            dummies.add(new MonthlyReport(id++, "November", 1001, "Pending", LocalDate.now().minusMonths(1)));
            dummies.add(new MonthlyReport(id++, "November", 1005, "Reviewed", LocalDate.now().minusMonths(1)));
            dummies.add(new MonthlyReport(id++, "December", 1001, "Pending", LocalDate.now().minusDays(5)));

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(dummies);
            } catch (Exception ignored) {}
        }
    }
}