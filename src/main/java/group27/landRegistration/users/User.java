package group27.landRegistration.users;

import group27.landRegistration.utility.customAlert;
import javafx.scene.control.Alert;

import java.time.LocalDate;

abstract public class User {
    private static int idCounter = 100000000;

    protected int userID;             // each user gets his/her own ID
    protected String name, password, Email, gender;
    protected long NID, phoneNumber;
    protected LocalDate DoB;


    public User(String name, String password, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        idCounter++;
        this.userID = idCounter;
        this.name = name;
        this.password = password;
        Email = email;
        this.gender = gender;
        this.NID = NID;
        this.phoneNumber = phoneNumber;
        DoB = doB;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        User.idCounter = idCounter;
    }

    public int getUserID() {
        return userID;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getNID() {
        return NID;
    }

    public void setNID(long NID) {
        this.NID = NID;
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
                ", gender='" + gender + '\'' +
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

    public void editProfile(String password, String name, String newPassword, String email, String gender, long NID, long phoneNumber, LocalDate doB) {
        if(this.password.equals(password)) {
            if(name != null) setName(name);
            if(newPassword != null) setPassword(newPassword);
            if(email != null) setEmail(email);
            if(gender!= null) setGender(gender);
            if(NID != 0 && String.valueOf(NID).length() == 17) setNID(NID);
            if(phoneNumber != 0 && String.valueOf(phoneNumber).length() == 10) setPhoneNumber(phoneNumber);
            if(doB !=null /* && something */) setDoB(doB);
        }
        else {
            CustomAlert alert = new CustomAlert(ERROR, "Error Alert!", "Wrong Password!", "Your confirm password must match with your given password.");

        }
    }

}