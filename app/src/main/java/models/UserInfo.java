package models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

public class UserInfo {
    private String email;
    private String name;
    private String universityId;
    private String userImage;
    private UserSettings settings;
    @Exclude
    private String password;

    public UserInfo() {
    }

    public UserInfo(String email, String name, String universityId, String userImage) {
        this.email = email;
        this.name = name;
        this.universityId = universityId;
        this.userImage = userImage;
    }

    public UserInfo(String email, String password, String name, String universityId, String userImage) {
        this.email = email;
        this.name = name;
        this.universityId = universityId;
        this.userImage = userImage;
        this.password = password;
    }

    public UserInfo(String email, String userImage, String universityId) {
        this.email = email;
        this.userImage = userImage;
        this.universityId = universityId;
    }

    public UserInfo(String email, String name, String universityId, String userImage, UserSettings settings) {
        this.email = email;
        this.name = name;
        this.universityId = universityId;
        this.userImage = userImage;
        this.settings = settings;
    }

    public UserSettings getSettings() {
        return settings;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    private Boolean isValidEmail(CharSequence target) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        return !target.toString().isEmpty() && target.toString().matches(emailPattern);
    }

    public String validateInput() {
        StringBuilder errorMessage = new StringBuilder();
        String fieldRequired = "Field is required";

        if (email == null || email.isEmpty()) {
            errorMessage.append("You must enter your institutional mail.\n");
        } else if (!isValidEmail(email)) {
            errorMessage.append("Please enter a valid email.\n");
        } else if (!email.endsWith("@jecc.ac.in")) {
            errorMessage.append("You must enter your institutional mail.\n");
        }

        if (universityId == null || universityId.isEmpty()) {
            errorMessage.append("University ID is required.\n");
        }

        if (password == null || password.isEmpty()) {
            errorMessage.append("Password is required.\n");
        }

        if (name == null || name.isEmpty()) {
            errorMessage.append("Name is required.\n");
        } else if (name.length() > 20) {
            errorMessage.append("Please enter a name of 20 characters.\n");
        }

        return errorMessage.length() > 0 ? errorMessage.toString().trim() : null;
    }
}
