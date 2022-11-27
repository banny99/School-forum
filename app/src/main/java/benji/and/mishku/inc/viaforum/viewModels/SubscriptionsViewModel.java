package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.contracts.SubscriptionService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.SubscriptionFirebaseRepository;

public class SubscriptionsViewModel extends AndroidViewModel {
    private SubscriptionService subscriptionService;

    public SubscriptionsViewModel(@NonNull Application application) {
        super(application);
        subscriptionService= SubscriptionFirebaseRepository.getInstance();
    }

    public void subscribeToSubforum(User user, Subforum subforum){
        subscriptionService.subscribeUserToSubforum(user,subforum);
    }
    public LiveData<List<Subforum>> getSubforumsForUser(User user){
        return subscriptionService.getSubscriptionsForUser(user);
    }

}
