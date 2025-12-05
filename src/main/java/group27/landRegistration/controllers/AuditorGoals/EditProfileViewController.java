package group27.landRegistration.controllers.AuditorGoals;

import group27.landRegistration.controllers.AllDashboards.AuditorDashboardViewController;
import group27.landRegistration.users.Auditor;
import group27.landRegistration.users.User;
import group27.landRegistration.utility.CustomAlert;
import group27.landRegistration.utility.CurrentPageLoader;
import group27.landRegistration.utility.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

public class EditProfileViewController {

    @FXML private DatePicker DoBDP;
    @FXML private PasswordField NewPasswordTF;
    @FXML private PasswordField ConfirmPasswordTF; // Acts as "Current Password" for verification
    @FXML private TextField EmailTF;
    @FXML private TextField PhoneTF;
    @FXML private TextField NameTF;

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user;

        // 1. Pre-fill fields with current data
        if (user != null) {
            NameTF.setText(user.getName());
            EmailTF.setText(user.getEmail());
            PhoneTF.setText(String.valueOf(user.getPhoneNumber()));
            DoBDP.setValue(user.getDoB());
        }
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    @FXML
    public void SaveProfileOA(ActionEvent actionEvent) {
        // 1. Validation: "Confirm Password" field is used to verify Identity
        String currentPass = ConfirmPasswordTF.getText();

        if (currentPass.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Security Check", "Password Required",
                    "Please enter your CURRENT password in the 'Confirm Password' box to authorize changes.");
            return;
        }

        try {
            long phone = Long.parseLong(PhoneTF.getText());
            String newPass = NewPasswordTF.getText().isEmpty() ? null : NewPasswordTF.getText();

            // 2. Update In-Memory Object using the User class method
            // Signature: editProfile(oldPass, name, newPass, email, gender, NID, phone, doB)
            // We pass 0/null for fields not present in this form (NID, Gender) to keep them unchanged.

            loggedInUser.editProfile(
                    currentPass,            // Old Password (Required)
                    NameTF.getText(),       // Name
                    newPass,                // New Password
                    EmailTF.getText(),      // Email
                    null,                   // Gender (Unchanged)
                    0,                      // NID (Unchanged)
                    phone,                  // Phone
                    DoBDP.getValue()        // DoB
            );

            // 3. Verification: Did the update succeed?
            // The User class prints an error if password doesn't match, but doesn't throw exception.
            // We verify by checking if the password currently stored matches our input.
            boolean isPasswordCorrect = loggedInUser.getPassword().equals(newPass != null ? newPass : currentPass);

            if (isPasswordCorrect) {
                // 4. Persistence: Save changes to users.dat
                FileManager<User> fm = new FileManager<>("users.dat");
                List<User> users = fm.loadList();

                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserID() == loggedInUser.getUserID()) {
                        users.set(i, loggedInUser);
                        break;
                    }
                }
                fm.saveList(users);

                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Profile Updated",
                        "Your profile details have been updated successfully.");

                // Clear password fields
                ConfirmPasswordTF.clear();
                NewPasswordTF.clear();
            } else {
                CustomAlert.show(Alert.AlertType.ERROR, "Error", "Update Failed",
                        "Incorrect current password provided. Changes were not saved.");
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Phone",
                    "Phone number must contain digits only.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void HomeOA(ActionEvent actionEvent) {
        // Safe navigation back to Auditor Dashboard
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/AuditorDashboardView.fxml",
                actionEvent,
                (AuditorDashboardViewController controller) -> {
                    controller.setUserData(loggedInUser);
                }
        );
    }
}