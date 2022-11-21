package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.contracts.SubscriptionService;
import benji.and.mishku.inc.viaforum.models.Subforum;

public class SubscriptionsViewModel extends AndroidViewModel {
    private SubscriptionService subscriptionService;

    public SubscriptionsViewModel(@NonNull Application application) {
        super(application);
        subscriptionService= SubscriptionsRepository.getInstance(application);
    }

    public void subscribeToSubforum(Long userId, Long subforumId){
        subscriptionService.subscribeUserToSubforum(userId,subforumId);
    }
    public LiveData<List<Subforum>> getSubforumsForUser(Long userId){
        return subscriptionService.getSubscriptionsForUser(userId);
    }


}
