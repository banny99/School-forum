package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.contracts.SubscriptionService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.repositories.SubscriptionFirebaseRepository;

public class SubscriptionsViewModel extends AndroidViewModel {
    private SubscriptionService subscriptionService;

    public SubscriptionsViewModel(@NonNull Application application) {
        super(application);
        subscriptionService= SubscriptionFirebaseRepository.getInstance(application);
    }

    public void subscribeToSubforum(String userId, String subforumId){
        subscriptionService.subscribeUserToSubforum(userId,subforumId);
    }
    public LiveData<List<Subforum>> getSubforumsForUser(String userId){
        return subscriptionService.getSubscriptionsForUser(userId);
    }


}
