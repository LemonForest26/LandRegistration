package group27.landRegistration.controllers.PublicUserGoals;

import group27.landRegistration.controllers.AllDashboards.PublicUserDashBoardViewController;
import group27.landRegistration.users.PublicUser;
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
    @FXML private TextField AddressTF; // Note: 'User' model usually doesn't have address, so this might not persist unless added to Model.
    @FXML private TextField EmailTF;
    @FXML private TextField PhoneTF;
    @FXML private TextField NameTF;
    @FXML private PasswordField NewPasswordTF;
    @FXML private PasswordField ConfirmPasswordTF; // Used as "Current Password" for verification

    private User loggedInUser;

    public void setUserData(User user) {
        this.loggedInUser = user;

        // 1. Pre-fill fields with existing data
        if (user != null) {
            NameTF.setText(user.getName());
            EmailTF.setText(user.getEmail());
            PhoneTF.setText(String.valueOf(user.getPhoneNumber()));
            DoBDP.setValue(user.getDoB());

            // Address logic: If your User model doesn't have an address field,
            // we leave this blank or load it from a separate file if it exists.
            AddressTF.setText("");
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
                    "Please enter your CURRENT password in the 'Confirm Password' box to save changes.");
            return;
        }

        try {
            long phone = Long.parseLong(PhoneTF.getText());
            String newPass = NewPasswordTF.getText().isEmpty() ? null : NewPasswordTF.getText();

            // 2. Update In-Memory Object
            // We use the User.editProfile method.
            // Note: We pass 0/null for fields we don't want to change (Gender, NID).

            loggedInUser.editProfile(
                    currentPass,            // Old Password (Required for verification)
                    NameTF.getText(),       // Name
                    newPass,                // New Password
                    EmailTF.getText(),      // Email
                    null,                   // Gender (Keep existing)
                    0,                      // NID (Keep existing)
                    phone,                  // Phone
                    DoBDP.getValue()        // DoB
            );

            // 3. Verify Success
            // The model prints to console if password fails. We check if the object updated.
            // A simple check is: does the stored password match our input (new or old)?
            boolean isVerified = loggedInUser.getPassword().equals(newPass != null ? newPass : currentPass);

            if (isVerified) {
                // 4. Persistence: Save to users.dat
                saveUserToFile();

                CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Profile Updated",
                        "Your profile details have been updated successfully.");

                // Clear password fields
                ConfirmPasswordTF.clear();
                NewPasswordTF.clear();
            } else {
                CustomAlert.show(Alert.AlertType.ERROR, "Update Failed", "Wrong Password",
                        "The current password you entered is incorrect. Changes were not saved.");
            }

        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Input Error", "Invalid Phone",
                    "Phone number must contain digits only.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUserToFile() {
        FileManager<User> fm = new FileManager<>("users.dat");
        List<User> users = fm.loadList();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID() == loggedInUser.getUserID()) {
                users.set(i, loggedInUser); // Update the object in the list
                break;
            }
        }
        fm.saveList(users);
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/PublicUserDashBoardView.fxml",
                actionEvent,
                (PublicUserDashBoardViewController controller) -> {
                    controller.setUserData(loggedInUser);
                }
        );
    }

    @Deprecated
    public void HomeOA(ActionEvent actionEvent) {
        // Not used, but kept to match your snippet structure
        BackOA(actionEvent);
    }
}