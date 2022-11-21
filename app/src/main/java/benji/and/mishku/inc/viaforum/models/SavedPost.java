package benji.and.mishku.inc.viaforum.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class SavedPost {
    private String userId;
    private String postId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public SavedPost() {
    }

    public SavedPost(String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
