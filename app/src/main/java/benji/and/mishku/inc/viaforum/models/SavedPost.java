package benji.and.mishku.inc.viaforum.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(primaryKeys = {"userId","postId"})

public class SavedPost {
    @NonNull
    private Long userId;
    @NonNull
    private Long postId;

    public SavedPost(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }


}
