package group27.landRegistration.users;

import java.time.LocalDate;

abstract public class User {
    private static int idCounter = 100000000;

    protected int userID;             // each user gets their own ID
    protected String name, password, Email;
    protected long NID, phoneNumber;
    protected LocalDate DoB;

    public User(String name, String password, String email, long NID, long phoneNumber, LocalDate doB) {
        this.userID = idCounter++;  // auto-generate ID here

        this.name = name;
        this.password = password;
        Email = email;
        this.NID = NID;
        this.phoneNumber = phoneNumber;
        DoB = doB;
    }

    public int getUserID() {
        return userID;
    }

    public long getNID() {
        return NID;
    }

    public void setNID(long NID) {
        this.NID = NID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDoB() {
        return DoB;
    }

    public void setDoB(LocalDate doB) {
        DoB = doB;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", Email='" + Email + '\'' +
                ", NID=" + NID +
                ", phoneNumber=" + phoneNumber +
                ", DoB=" + DoB +
                '}';
    }

    public void editProfile(String oldPassword, String newName, String newEmail, LocalDate newDoB) {
        if (!this.password.equals(oldPassword)) {
            System.out.println("Incorrect password! Cannot edit profile."); //Will be replaced with warning later!
            return;
        }

        if (newName != null && !newName.isEmpty()) {
            this.name = newName;
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            this.Email = newEmail;
        }
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
        if (phoneNumber > 0) {
            this.phoneNumber = phoneNumber;
        }
        if (newDoB != null) {
            this.DoB = newDoB;
        }
    }

    public void logOut() {

    }

}