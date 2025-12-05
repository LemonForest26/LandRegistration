package group27.landRegistration.controllers.LandRegistrarGoals;

import group27.landRegistration.controllers.AllDashboards.LandRegistrarDashBoardViewController;
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

        // Pre-fill data
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
        // 1. Validation
        String currentPass = ConfirmPasswordTF.getText();

        if (currentPass.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Security Check", "Password Required",
                    "Please enter your current password in the 'Confirm Password' box to save changes.");
            return;
        }

        try {
            long phone = Long.parseLong(PhoneTF.getText());
            String newPass = NewPasswordTF.getText().isEmpty() ? null : NewPasswordTF.getText();

            // 2. Update In-Memory Object
            // Using the specific signature from your User class:
            // editProfile(password, name, newPassword, email, gender, NID, phoneNumber, doB)

            // Capture old state to check if update happens
            String oldName = loggedInUser.getName();

            loggedInUser.editProfile(
                    currentPass,            // password (verification)
                    NameTF.getText(),       // name
                    newPass,                // newPassword
                    EmailTF.getText(),      // email
                    null,                   // gender (keep existing)
                    0,                      // NID (keep existing)
                    phone,                  // phoneNumber
                    DoBDP.getValue()        // doB
            );

            // 3. Verification & File Saving
            // The User.editProfile method shows an Alert if the password is wrong.
            // We check if the password provided matches the user's password to determine if we should save.
            if (loggedInUser.getPassword().equals(currentPass) || (newPass != null && loggedInUser.getPassword().equals(newPass))) {

                // Save to users.dat
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

                // Clear sensitive fields
                ConfirmPasswordTF.clear();
                NewPasswordTF.clear();
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Phone",
                    "Phone number must be numeric.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandRegistrarDashBoardView.fxml",
                actionEvent,
                (LandRegistrarDashBoardViewController controller) -> {
                    controller.setUserData(loggedInUser);
                }
        );
    }
}