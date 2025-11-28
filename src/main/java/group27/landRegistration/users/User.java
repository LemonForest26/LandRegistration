package group27.landRegistration.users;

import java.time.LocalDate;

abstract public class User {
    protected String name, password, Email;
    protected long NID, phoneNumber;
    protected LocalDate DoB;

    public User(String name, String password, String email, long NID, long phoneNumber, LocalDate doB) {
        this.name = name;
        this.password = password;
        Email = email;
        this.NID = NID;
        this.phoneNumber = phoneNumber;
        DoB = doB;
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
                "name='" + name + '\'' +
                ", Email='" + Email + '\'' +
                ", NID=" + NID +
                ", phoneNumber=" + phoneNumber +
                ", DoB=" + DoB +
                '}';
    }

    public void editProfile() {
    }
}