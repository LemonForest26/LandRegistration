package group27.landRegistration.users;

import java.time.LocalDate;

public class Auditor extends User {
    public Auditor(String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, gender, NID, phoneNumber, doB);
    }
}
