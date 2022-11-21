package benji.and.mishku.inc.viaforum.repositories;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.UserService;
import benji.and.mishku.inc.viaforum.models.User;

public class UserFirebaseRepository implements UserService {
    private static volatile UserFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference usersRef;

    private UserFirebaseRepository(){
        usersRef = FirebaseDatabase.getInstance().getReference("users");
    }

    public static UserFirebaseRepository getInstance(){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new UserFirebaseRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void addUser(User user) {
        usersRef.child(user.getUserId()).setValue(user);
    }

    @Override
    public void deleteUser(String userId) {
        usersRef.child(userId).setValue(null);
    }

    @Override
    public void updateUser(User user) {
        usersRef.child(user.getUserId()).setValue(user);
    }

    @Override
    public void getUserById(String uid, MyCallBack callback) {
        Query q = usersRef.orderByChild("userId").equalTo(uid);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                callback.callback(u);
//                u = q.get().getResult().getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface MyCallBack{
        void callback(User user);
    }
}

