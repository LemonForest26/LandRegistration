package group27.landRegistration.nonUsers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private String category; // "Announcement", "Auction", "Zoning"
    private LocalDate date;

    public Notice(String title, String content, String category, LocalDate date) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.date = date;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return "[" + date + "] " + title;
    }

    // --- STATIC BLOCK TO GENERATE DUMMY DATA FOR TESTING ---
    static {
        File file = new File("Notice.dat");
        if (!file.exists() || file.length() == 0) {
            List<Notice> dummies = new ArrayList<>();

            dummies.add(new Notice("Server Maintenance", "System will be down on Friday 10 PM.", "Announcement", LocalDate.now()));
            dummies.add(new Notice("Office Holiday", "Land Office closed for Eid.", "Announcement", LocalDate.now().minusDays(2)));

            dummies.add(new Notice("Plot 1005 Auction", "Prime location in Banani. Starting bid 5Cr.", "Auction", LocalDate.now().minusDays(5)));
            dummies.add(new Notice("Govt Land Lease", "Lease available for Plot 888.", "Auction", LocalDate.now().minusDays(10)));

            dummies.add(new Notice("Zone A Commercialized", "Plot 1000-1050 are now Commercial zones.", "Zoning", LocalDate.now().minusDays(1)));
            dummies.add(new Notice("Green Belt Updates", "New restrictions on construction near river.", "Zoning", LocalDate.now().minusDays(20)));

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(dummies);
            } catch (Exception ignored) {}
        }
    }
}