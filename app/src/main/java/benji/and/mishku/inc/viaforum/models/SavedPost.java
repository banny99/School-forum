package benji.and.mishku.inc.viaforum.models;

import androidx.room.PrimaryKey;

public class SavedPost {
    @PrimaryKey
    private Long userId;
    @PrimaryKey
    private Long postId;

    public SavedPost(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
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
