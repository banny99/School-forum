package benji.and.mishku.inc.viaforum.models;


public class Subscription {
    private String subscriptionId;
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

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
