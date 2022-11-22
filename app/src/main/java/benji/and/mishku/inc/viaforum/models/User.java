package benji.and.mishku.inc.viaforum.models;


public class User {

    private String userId;
    private String username;
    private String email;

    public User() {
        //required by Firebase
    }

    public User(String userId, String email) {
        this.userId = userId;
        this.email = email;
        username = email.substring(0,email.indexOf('@'));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
