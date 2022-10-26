package benji.and.mishku.inc.viaforum;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private String password;
    private List<Post> myPosts;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        myPosts = new ArrayList<>();
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

    public List<Post> getMyPosts() {
        return myPosts;
    }

    public void setMyPosts(List<Post> myPosts) {
        this.myPosts = myPosts;
    }

    public void addPost(Post p){
        myPosts.add(p);
    }

    public void deletePost(Post p){
        myPosts.remove(p);
    }
}
