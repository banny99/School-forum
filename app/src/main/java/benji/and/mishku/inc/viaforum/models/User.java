package benji.and.mishku.inc.viaforum.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private DateTime birthday;
    private Long subForumId;

    public User() {
        //required by Firebase
    }

    public User(String username, String email, String password, DateTime birthday) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public Long getSubForumId() {
        return subForumId;
    }

    public void setSubForumId(Long subForumId) {
        this.subForumId = subForumId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public DateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
    }
}
