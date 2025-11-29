package group27.landRegistration.users;

import java.time.LocalDate;

public class LandRegistrar extends User{
    public LandRegistrar(String name, String password, String email, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, NID, phoneNumber, doB);
    }


}
