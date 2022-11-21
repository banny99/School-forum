package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public interface SubscriptionService {
    LiveData<List<Subforum>> getSubscriptionsForUser(String userId);
    LiveData<List<User>> getUsersSubscribedToSubforum(String subId);
    void subscribeUserToSubforum(String userId, String subforumId);
    void unSubscribeUserFromSubforum(String subscriptionId);
}
