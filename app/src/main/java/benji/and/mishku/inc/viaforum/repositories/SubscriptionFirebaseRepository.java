package benji.and.mishku.inc.viaforum.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.contracts.SubscriptionService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public class SubscriptionFirebaseRepository implements SubscriptionService {

    //ToDo: do the implementation with firebase

    @Override
    public LiveData<List<Subforum>> getSubscriptionsForUser(Long userId) {
        return null;
    }

    @Override
    public LiveData<List<User>> getUsersSubscribedToSubforum(Long subId) {
        return null;
    }

    @Override
    public void subscribeUserToSubforum(Long userId, Long subforumId) {

    }

    @Override
    public void unSubscribeUserFromSubforum(Long userId, Long subforumId) {

    }
}
