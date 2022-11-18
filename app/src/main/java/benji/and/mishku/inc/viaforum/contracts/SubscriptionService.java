package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.Subscription;
import benji.and.mishku.inc.viaforum.models.User;

public interface SubscriptionService {
    LiveData<List<Subforum>> getSubscriptionsForUser(Long userId);
    LiveData<List<User>> getUsersSubscribedToSubforum(Long subId);
    void subscribeUserToSubforum(Long userId, Long subforumId);
    void unSubscribeUserFromSubforum(Long userId, Long subforumId);
}
