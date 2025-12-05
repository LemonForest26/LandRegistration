package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idCounter = 1000;

    // Static block to auto-increment ID based on last saved file
    static {
        try {
            File file = new File("Feedback.dat");
            if (file.exists() && file.length() > 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<Feedback> feeds = (List<Feedback>) ois.readObject();
                ois.close();
                if (!feeds.isEmpty()) {
                    idCounter = feeds.get(feeds.size() - 1).getFeedbackID();
                }
            }
        } catch (Exception ignored) { }
    }

    private int feedbackID, submitterID;
    private String subject, message;
    private LocalDate submissionDate;

    public Feedback(int submitterID, String subject, String message) {
        idCounter++;
        this.feedbackID = idCounter;
        this.submitterID = submitterID;
        this.subject = subject;
        this.message = message;
        this.submissionDate = LocalDate.now();
    }

    public int getFeedbackID() { return feedbackID; }
    public int getSubmitterID() { return submitterID; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
    public LocalDate getSubmissionDate() { return submissionDate; }

    @Override
    public String toString() {
        return "Feedback{" + "ID=" + feedbackID + ", Subject='" + subject + '\'' + '}';
    }
}