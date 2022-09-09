package model;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private HashedPassword passwordHash;
    private String profilePhoto;
    private boolean premium;

    public User(String firstName, String lastName, String username, String password, String profilePhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = new HashedPassword(password);
        this.profilePhoto = profilePhoto;
        this.premium = false;
    }

    public User(String firstName, String lastName, String username, HashedPassword password, String profilePhoto, boolean premium) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = password;
        this.profilePhoto = profilePhoto;
        this.premium = premium;
    }

    public void subscribe() {
        if (!this.premium) this.premium = true;
    }

    public void unsubscribe() {
        if (this.premium) this.premium = false;
    }

    public void updatePassword(String password) {
        this.passwordHash = new HashedPassword(password);
    }

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public HashedPassword getPasswordHash() {
        return this.passwordHash;
    }
    public String getProfilePhoto() {
        return this.profilePhoto;
    }
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
    public boolean getPremium() {
        return this.premium;
    }

    public String getName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}

