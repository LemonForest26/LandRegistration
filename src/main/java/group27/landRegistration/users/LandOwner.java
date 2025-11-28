package group27.landRegistration.users;

import java.time.LocalDate;

public class LandOwner extends PublicUser{

    public LandOwner(String address, String name, String password, String email, long NID, long phoneNumber, LocalDate doB) {
        super(address, name, password, email, NID, phoneNumber, doB);
    }
}
