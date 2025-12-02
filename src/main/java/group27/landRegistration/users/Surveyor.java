package group27.landRegistration.users;

import java.time.LocalDate;

public class Surveyor extends User{
    public Surveyor(int userID, String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        super(userID, name, password, email, gender, NID, phoneNumber, doB);
    }
}
