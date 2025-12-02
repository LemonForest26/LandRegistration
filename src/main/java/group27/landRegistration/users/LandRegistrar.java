package group27.landRegistration.users;

import java.time.LocalDate;

public class LandRegistrar extends User{
    public LandRegistrar(int userID, String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(userID, name, password, email, gender, NID, phoneNumber, doB);
    }
}
