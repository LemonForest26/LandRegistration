package group27.landRegistration.users;

import java.time.LocalDate;

public class PublicUser extends User{
    protected String address;
    public PublicUser(String address, String name, String password, String email, long NID, long phoneNumber, LocalDate doB) {
        super(name, password, email, NID, phoneNumber, doB);
        this.address = address;
    }
}
