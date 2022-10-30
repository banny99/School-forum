package benji.and.mishku.inc.viaforum.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String content;
    private Long userId;
    private Long postId;

    public Comment( String content, Long userId, Long postId) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
