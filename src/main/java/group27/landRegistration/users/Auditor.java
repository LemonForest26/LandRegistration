package group27.landRegistration.users;

import java.time.LocalDate;

public class Auditor extends User {
    public Auditor(String name, String password, String email, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, NID, phoneNumber, doB);
    }
}
