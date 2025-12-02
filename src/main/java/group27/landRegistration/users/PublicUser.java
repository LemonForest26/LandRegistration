package group27.landRegistration.users;

import java.time.LocalDate;

public class PublicUser extends User{
    protected String address;

    public PublicUser(int userID, String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB, String address) {
        super(userID, name, password, email, gender, NID, phoneNumber, doB);
        this.address = address;
    }
}
