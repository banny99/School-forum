package benji.and.mishku.inc.viaforum.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.SubscriptionService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public class SubscriptionFirebaseRepository implements SubscriptionService {
    private static volatile SubscriptionFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference usersRef;
    private final DatabaseReference subforumsRef;

    private SubscriptionFirebaseRepository(){
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        subforumsRef = FirebaseDatabase.getInstance().getReference("subforums");
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
    public LiveData<List<Subforum>> getSubscriptionsForUser(User user) {
        MutableLiveData<List<Subforum>> subscriptions=new MutableLiveData<>();
        usersRef.child(user.getUserId()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               subscriptions.setValue(Objects.requireNonNull(snapshot.getValue(User.class)).getSubscriptions());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               try {
                   throw new Exception(error.getMessage());
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }
        );
        return subscriptions;
    }

    @Override
    //ToDo: not finished ...
    public LiveData<List<User>> getUsersSubscribedToSubforum(String subforumId) {
        //get userIDs from subscriptions:
        ArrayList<String> userIDs= new ArrayList<>();
        Query q = usersRef.orderByChild("subforumId").equalTo(subforumId);
        for (DataSnapshot s : q.get().getResult().getChildren())
            userIDs.add(s.getValue(String.class));

        //Get the list of users with IDs in userIDs-list:
        ArrayList<User> tempUsers = new ArrayList<>();
        for (String userId : userIDs){
            Query q2 = FirebaseDatabase.getInstance().getReference("users").orderByChild("userId").equalTo(userId);
            q2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        return null;
    }

    @Override
    public void subscribeUserToSubforum(User user, Subforum subforum) {
        user.getSubscriptions().add(subforum);
        usersRef.child(user.getUserId()).setValue(user);
    }

    @Override
    public void unSubscribeUserFromSubforum(User user, Subforum subforum) {
        user.getSubscriptions().remove(subforum);
        usersRef.child(user.getUserId()).setValue(user);
    }


}
