package group27.landRegistration.users;

import group27.landRegistration.nonUsers.Certificate;
import group27.landRegistration.nonUsers.Plot;
import group27.landRegistration.utility.FileManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LandOwner extends User {

    public LandOwner(String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, gender, NID, phoneNumber, doB);
    }

    // --- GOAL 1: VIEW CERTIFICATES ---
    public List<Certificate> viewMyCertificates() {
        FileManager<Certificate> manager = new FileManager<>("Certificate.dat");
        List<Certificate> allCerts = manager.loadList();

        // Debug Print: Check what is being loaded
        System.out.println("Total Certs in file: " + allCerts.size());
        System.out.println("My User ID: " + this.getUserID());

        // Filter: Match Certificate Owner ID with Current User ID
        return allCerts.stream()
                .filter(c -> c.getOwnerID() == this.getUserID())
                .collect(Collectors.toList());
    }

    // --- GOAL 2: DOWNLOAD LOGIC ---
    public void downloadCertificateFile(Certificate cert, File destinationFile) throws IOException {
        File sourceFile = new File(cert.getCertificationFilePath());
        if (!sourceFile.exists()) {
            throw new IOException("System Error: Server file not found at " + sourceFile.getAbsolutePath());
        }
        Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public List<Plot> viewMyPlots() {
        FileManager<Plot> manager = new FileManager<>("Plot.dat");
        List<Plot> allPlots = manager.loadList();

        // Filter: Match Plot Owner ID with Current User ID
        return allPlots.stream()
                .filter(p -> p.getOwnerID() == this.getUserID())
                .collect(Collectors.toList());
    }
}