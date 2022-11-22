package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public interface SubscriptionService {
    LiveData<List<Subforum>> getSubscriptionsForUser(User user);
    LiveData<List<User>> getUsersSubscribedToSubforum(String subId);
    void subscribeUserToSubforum(User user, Subforum subforum);
    void unSubscribeUserFromSubforum(User user, Subforum subforum);
}
