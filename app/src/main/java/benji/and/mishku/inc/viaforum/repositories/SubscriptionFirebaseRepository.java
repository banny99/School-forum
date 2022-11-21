package benji.and.mishku.inc.viaforum.repositories;

import androidx.lifecycle.LiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.SubscriptionService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public class SubscriptionFirebaseRepository implements SubscriptionService {
    private static volatile SubscriptionFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference subscriptionsRef;

    private SubscriptionFirebaseRepository(){
        subscriptionsRef = FirebaseDatabase.getInstance().getReference("subscriptions");
    }

    public static SubscriptionFirebaseRepository getInstance(){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new SubscriptionFirebaseRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public LiveData<List<Subforum>> getSubscriptionsForUser(String userId) {
        return null;
    }

    @Override
    public LiveData<List<User>> getUsersSubscribedToSubforum(String subId) {
        return null;
    }

    @Override
    public void subscribeUserToSubforum(String userId, String subforumId) {

    }

    @Override
    public void unSubscribeUserFromSubforum(String userId, String subforumId) {

    }

    //ToDo: do the implementation with firebase


}
