package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable; // <--- ADDED
import java.time.LocalDate;
import java.util.List;

public class Report implements Serializable { // <--- ADDED
    private static final long serialVersionUID = 1L;
    private static int IDCounter = 1000;

    static {
        try {
            File file = new File("Report.dat");
            if (file.exists() && file.length() > 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<Report> reps = (List<Report>) ois.readObject();
                ois.close();
                if (!reps.isEmpty()) {
                    IDCounter = reps.get(reps.size() - 1).getReportID();
                }
            }
        } catch (Exception ignored) { }
    }

    private int ReportID, GeneratedByID;
    private String type, filePath, content;
    private LocalDate generatedOn;

    public Report(int generatedByID, String type, String filePath, String content, LocalDate generatedOn) {
        IDCounter++;
        this.ReportID = IDCounter;
        this.GeneratedByID = generatedByID;
        this.type = type;
        this.filePath = filePath;
        this.content = content;
        this.generatedOn = generatedOn;
    }

    public int getReportID() { return ReportID; }
    public int getGeneratedByID() { return GeneratedByID; }
    public String getType() { return type; }
    public String getFilePath() { return filePath; }
    public String getContent() { return content; }
    public LocalDate getGeneratedOn() { return generatedOn; }

    @Override
    public String toString() {
        return "Report #" + ReportID + ": " + type;
    }
}