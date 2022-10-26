package benji.and.mishku.inc.viaforum;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Post {
    private String title;
    private String postText;
    private User author;
    private PostCategory category;
    private SubForum subForum;
    private LocalDateTime timestamp;

    public Post(String title, String postText, User author, PostCategory category, SubForum subForum, LocalDateTime timestamp) {
        this.title = title;
        this.postText = postText;
        this.author = author;
        this.category = category;
        this.subForum = subForum;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public PostCategory getCategory() {
        return category;
    }

    public void setCategory(PostCategory category) {
        this.category = category;
    }

    public SubForum getSubForum() {
        return subForum;
    }

    public void setSubForum(SubForum subForum) {
        this.subForum = subForum;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

