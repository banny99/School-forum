package benji.and.mishku.inc.viaforum;

import java.sql.Timestamp;

public class Post {
    private String postText;
    private User author;
    private PostCategory category;
    private SubForum subForum;
    private Timestamp timestamp;

    public Post(String postText, User author, PostCategory category, SubForum subForum, Timestamp timestamp) {
        this.postText = postText;
        this.author = author;
        this.category = category;
        this.subForum = subForum;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

