package benji.and.mishku.inc.viaforum.repositories;

import androidx.lifecycle.LiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.SubscriptionService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.Subscription;
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
    //ToDo: not finished ...
    public LiveData<List<User>> getUsersSubscribedToSubforum(String subforumId) {
        //get userIDs from subscriptions:
        ArrayList<String> userIDs= new ArrayList<>();
        Query q = subscriptionsRef.orderByChild("subforumId").equalTo(subforumId);
        for (DataSnapshot s : q.get().getResult().getChildren())
            userIDs.add(s.getValue(String.class));

        //Get the list of users with IDs in userIDs-list:
        ArrayList<User> tempUsers = new ArrayList<>();
        for (String userId : userIDs){
            Query q2 = FirebaseDatabase.getInstance().getReference("users").orderByChild("userId").equalTo(userId);

        }

        return null;
    }

    @Override
    public void subscribeUserToSubforum(String userId, String subforumId) {
        Subscription newSubscription = new Subscription(userId, subforumId);
        String id = subscriptionsRef.push().getKey();
        newSubscription.setSubscriptionId(id);
        subscriptionsRef.push().setValue(newSubscription);
    }

    @Override
    public void unSubscribeUserFromSubforum(String subscriptionId) {
        subscriptionsRef.child(subscriptionId).setValue(null);
    }
}
