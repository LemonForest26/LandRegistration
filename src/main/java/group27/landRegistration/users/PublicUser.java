package group27.landRegistration.users;

import group27.landRegistration.nonUsers.Certificate;
import group27.landRegistration.nonUsers.Feedback;
import group27.landRegistration.nonUsers.Notice;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.utility.FileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PublicUser extends User {

    public PublicUser(String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, gender, NID, phoneNumber, doB);
    }

    // ... (Keep existing methods: getFormTemplates, downloadForm, searchPlots, submitGeneralRequest, verifyCertificate, trackRequest) ...

    /**
     * Goal: View Notices
     * Logic: Loads notices from file and filters by category (Announcement, Auction, Zoning).
     */
    public List<Notice> getNotices(String category) {
        FileManager<Notice> fm = new FileManager<>("Notice.dat");
        List<Notice> allNotices = fm.loadList();

        return allNotices.stream()
                .filter(n -> n.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // --- RE-INCLUDING PREVIOUS METHODS TO ENSURE FILE COMPLETENESS ---

    public Map<String, String> getFormTemplates() {
        Map<String, String> forms = new HashMap<>();
        forms.put("Land Registration Application", "=== LAND REGISTRATION APPLICATION FORM ===\n\n...");
        forms.put("Mutation Request Form", "=== MUTATION REQUEST FORM ===\n\n...");
        forms.put("Tax Assessment Appeal", "=== TAX ASSESSMENT APPEAL ===\n\n...");
        return forms;
    }

    public void downloadForm(String formName, File destinationFile) throws IOException {
        String content = getFormTemplates().get(formName);
        if (content == null) throw new IOException("Form template not found.");
        try (FileWriter writer = new FileWriter(destinationFile)) {
            writer.write(content);
        }
    }

    public List<Plot> searchPlots(String plotIDStr, String locationText) {
        FileManager<Plot> fm = new FileManager<>("Plot.dat");
        return fm.loadList().stream().filter(p -> {
            if (plotIDStr != null && !plotIDStr.isEmpty()) {
                try {
                    if (p.getPlotID() != Integer.parseInt(plotIDStr)) return false;
                } catch (NumberFormatException ignored) { return false; }
            }
            if (locationText != null && !locationText.isEmpty()) {
                if (!p.getLocation().toLowerCase().contains(locationText.toLowerCase())) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    public void submitGeneralRequest(String requestType, String message) {
        Feedback request = new Feedback(this.getUserID(), requestType, message);
        new FileManager<Feedback>("Feedback.dat").appendItem(request);
    }

    public Certificate verifyCertificate(int certificateID) {
        return new FileManager<Certificate>("Certificate.dat").find(c -> c.getCertificateID() == certificateID);
    }

    public Feedback trackRequest(int requestID) {
        return new FileManager<Feedback>("Feedback.dat").find(f -> f.getFeedbackID() == requestID);
    }
}