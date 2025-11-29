package group27.landRegistration.nonUsers;

import java.time.LocalDate;

public class Report {
   private static int IDCounter = 100000;

    private int ReportID, GeneratedByID;
    private String generatedByName, type, filePath, content;
    private LocalDate generatedOn;

    public Report(int generatedByID, String generatedByName, String type, String filePath, String content, LocalDate generatedOn) {
        this.ReportID = IDCounter++;
        this.GeneratedByID = generatedByID;
        this.generatedByName = generatedByName;
        this.type = type;
        this.filePath = filePath;
        this.content = content;
        this.generatedOn = generatedOn;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public static void setIDCounter(int IDCounter) {
        Report.IDCounter = IDCounter;
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

    public String getGeneratedByName() {
        return generatedByName;
    }

    public void setGeneratedByName(String generatedByName) {
        this.generatedByName = generatedByName;
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

    public void saveReport(String path) {

    }

    public String exportAsPDF() {
        return "";
    }
}
