package group27.landRegistration.users;

import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.utility.FileManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PublicUser extends User {

    public PublicUser(String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, gender, NID, phoneNumber, doB);
    }
    public Map<String, String> getFormTemplates() {
        Map<String, String> forms = new HashMap<>();

        forms.put("Land Registration Application",
                "=== LAND REGISTRATION APPLICATION FORM ===\n\n" +
                        "1. Applicant Name: __________________________\n" +
                        "2. Father's Name:  __________________________\n" +
                        "3. NID Number:     __________________________\n" +
                        "4. Address:        __________________________\n" +
                        "5. Plot Details:\n" +
                        "   - Location:     __________________________\n" +
                        "   - Size (sq.ft): __________________________\n" +
                        "\n[ ] I certify that the information above is true.\n" +
                        "Signature: __________________  Date: __________");

        forms.put("Mutation Request Form",
                "=== MUTATION REQUEST FORM ===\n\n" +
                        "1. Current Owner ID: ________________________\n" +
                        "2. Plot ID:          ________________________\n" +
                        "3. Reason for Mutation:\n" +
                        "   [ ] Sale  [ ] Inheritance  [ ] Gift\n" +
                        "\n4. New Owner Details:\n" +
                        "   - Name: __________________________________\n" +
                        "   - NID:  __________________________________\n" +
                        "\nSignature: __________________");

        forms.put("Tax Assessment Appeal",
                "=== TAX ASSESSMENT APPEAL ===\n\n" +
                        "To the Land Revenue Office,\n\n" +
                        "I, __________________________ (Name), owner of Plot ID _________,\n" +
                        "wish to appeal the tax assessment for the year _______.\n\n" +
                        "Reason for Appeal:\n" +
                        "_______________________________________________________\n" +
                        "_______________________________________________________\n" +
                        "\nContact Phone: __________________");

        return forms;
    }


    public void downloadForm(String formName, File destinationFile) throws IOException {
        String content = getFormTemplates().get(formName);

        if (content == null) {
            throw new IOException("Form template not found.");
        }

        try (FileWriter writer = new FileWriter(destinationFile)) {
            writer.write(content);
        }
    }

    public List<Plot> searchPlots(String plotIDStr, String locationText) {
        FileManager<Plot> fm = new FileManager<>("Plot.dat");
        List<Plot> allPlots = fm.loadList();

        return allPlots.stream()
                .filter(p -> {
                    // 1. Filter by Plot ID (if provided)
                    if (plotIDStr != null && !plotIDStr.isEmpty()) {
                        try {
                            int searchID = Integer.parseInt(plotIDStr);
                            if (p.getPlotID() != searchID) return false;
                        } catch (NumberFormatException ignored) {
                            return false; // Invalid number input = no match
                        }
                    }

                    // 2. Filter by Location Name (Partial Match)
                    if (locationText != null && !locationText.isEmpty()) {
                        if (!p.getLocation().toLowerCase().contains(locationText.toLowerCase())) return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }
    public void submitGeneralRequest(String requestType, String message) {
        // We reuse the Feedback class where 'Subject' acts as the 'Request Type'
        group27.landRegistration.nonUsers.Feedback request =
                new group27.landRegistration.nonUsers.Feedback(this.getUserID(), requestType, message);

        // Save to Feedback.dat (Serves as a central inbox for admins/auditors)
        group27.landRegistration.utility.FileManager<group27.landRegistration.nonUsers.Feedback> fm =
                new group27.landRegistration.utility.FileManager<>("Feedback.dat");

        fm.appendItem(request);
    }
    public group27.landRegistration.nonUsers.Certificate verifyCertificate(int certificateID) {
        group27.landRegistration.utility.FileManager<group27.landRegistration.nonUsers.Certificate> fm =
                new group27.landRegistration.utility.FileManager<>("Certificate.dat");

        // Use the find method from FileManager
        return fm.find(c -> c.getCertificateID() == certificateID);
    }
    public group27.landRegistration.nonUsers.Feedback trackRequest(int requestID) {
        group27.landRegistration.utility.FileManager<group27.landRegistration.nonUsers.Feedback> fm =
                new group27.landRegistration.utility.FileManager<>("Feedback.dat");

        return fm.find(f -> f.getFeedbackID() == requestID);
    }
}