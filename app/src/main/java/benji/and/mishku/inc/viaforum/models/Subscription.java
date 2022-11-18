package benji.and.mishku.inc.viaforum.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"userId","subforumId"})

public class Subscription {
    @NonNull
    private Long userId;
    @NonNull
    private Long subforumId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubforumId() {
        return subforumId;
    }

    public void setSubforumId(Long subforumId) {
        this.subforumId = subforumId;
    }
}
