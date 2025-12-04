package group27.landRegistration.controllers.LandOwnerGoals;

import group27.landRegistration.controllers.AllDashboards.LandOwnerDashBoardViewController;
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

import java.time.LocalDate;
import java.util.List;

public class EditProfileViewController {

    private User loggedInUser;

    @FXML private TextField PhoneTF;
    @FXML private DatePicker DoBDP;
    @FXML private TextField EmailTF;
    @FXML private TextField NameTF;

    @FXML private PasswordField OldPasswordTF;

    @FXML private PasswordField NewPasswordTF;
    @FXML private PasswordField ConfirmPasswordTF;

    public void setUserData(User user) {
        this.loggedInUser = user;

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
        String currentPass = OldPasswordTF.getText();
        String newPass = NewPasswordTF.getText();
        String confirmPass = ConfirmPasswordTF.getText();

        if (currentPass.isEmpty()) {
            CustomAlert.show(Alert.AlertType.ERROR, "Validation Error", "Password Required",
                    "You must enter your current password to save changes.");
            return;
        }

        String passwordToSet = null;
        if (!newPass.isEmpty()) {
            if (!newPass.equals(confirmPass)) {
                CustomAlert.show(Alert.AlertType.ERROR, "Validation Error", "Mismatch",
                        "New password and Confirm password do not match.");
                return;
            }
            passwordToSet = newPass;
        }

        long phoneNumber = 0;
        try {
            phoneNumber = Long.parseLong(PhoneTF.getText());
        } catch (NumberFormatException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Validation Error", "Invalid Phone",
                    "Phone number must contain digits only.");
            return;
        }


        String originalName = loggedInUser.getName(); // Store to check if update worked

        loggedInUser.editProfile(
                currentPass,
                NameTF.getText(),
                passwordToSet,
                EmailTF.getText(),
                null,
                0,
                phoneNumber,
                DoBDP.getValue()
        );


        if (!loggedInUser.getPassword().equals(currentPass) && !loggedInUser.getPassword().equals(newPass)) {

        }

        saveChangesToFile();

        CustomAlert.show(Alert.AlertType.INFORMATION, "Success", "Profile Updated",
                "Your profile details have been saved.");
    }

    private void saveChangesToFile() {
        FileManager<User> userManager = new FileManager<>("users.dat");
        List<User> users = userManager.loadList();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID() == loggedInUser.getUserID()) {
                users.set(i, loggedInUser); // Replace old object with updated one
                break;
            }
        }

        userManager.saveList(users);
    }

    @FXML
    public void BackOA(ActionEvent actionEvent) {
        new CurrentPageLoader().loadWithData(
                "/group27/landRegistration/AllDashboards/LandOwnerDashBoardView.fxml",
                actionEvent,
                (LandOwnerDashBoardViewController controller) -> {
                    controller.setUserData(loggedInUser);
                }
        );
    }

}