package group27.landRegistration.users;

import java.time.LocalDate;

public class LandOwner extends User{
    public LandOwner(String name, String password, String email, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, NID, phoneNumber, doB);
    }
}
