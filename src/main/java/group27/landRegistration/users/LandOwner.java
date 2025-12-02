package group27.landRegistration.users;

import java.time.LocalDate;

public class LandOwner extends PublicUser{
    public LandOwner(int userID, String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB, String address) {
        super(userID, name, password, email, gender, NID, phoneNumber, doB, address);
    }
}
