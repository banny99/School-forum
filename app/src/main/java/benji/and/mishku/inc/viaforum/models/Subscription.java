package benji.and.mishku.inc.viaforum.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Subscription {
    private String userId;
    private String subforumId;

    public Subscription() {
    }

    public Subscription(String userId, String subforumId) {
        this.userId = userId;
        this.subforumId = subforumId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubforumId() {
        return subforumId;
    }

    public void setSubforumId(String subforumId) {
        this.subforumId = subforumId;
    }
}
