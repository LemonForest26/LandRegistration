package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.List;

public class Report {
   private static int IDCounter = 1000;

    static {
        try {
            File file = new File("Report.dat");
            if (file.exists() && file.length() > 0) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<Report> reps = (List<Report>) ois.readObject();
                ois.close();

                if (!reps.isEmpty()) {
                    int lastID = reps.get(reps.size() - 1).getReportID();
                    IDCounter = lastID;
                }
            }
        } catch (Exception ignored) {
            // If failed → keep default 1000
        }
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


    public int getReportID() {
        return ReportID;
    }

    public void setReportID(int reportID) {
        ReportID = reportID;
    }

    public int getGeneratedByID() {
        return GeneratedByID;
    }

    public void setGeneratedByID(int generatedByID) {
        GeneratedByID = generatedByID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDate generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Override
    public String toString() {
        return "Report{" +
                "ReportID=" + ReportID +
                ", GeneratedByID=" + GeneratedByID +
                ", type='" + type + '\'' +
                ", filePath='" + filePath + '\'' +
                ", content='" + content + '\'' +
                ", generatedOn=" + generatedOn +
                '}';
    }

    public void saveReport(String path) {

    }

    public String exportAsPDF() {
        return "";
    }
}
