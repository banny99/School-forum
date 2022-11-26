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
import benji.and.mishku.inc.viaforum.contracts.UserService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
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
    public void getUserById(String uid, UserCallBack callback) {
        usersRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                callback.callback(u);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public LiveData<User> getPostAuthor(Post post) {
        MutableLiveData<User> user=new MutableLiveData<>();
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    User u = postSnapshot.getValue(User.class);
                    assert u != null;
                    if(Objects.equals(u.getUserId(), post.getUserId())){
                        user.setValue(u);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return user;
    }

    @Override
    public LiveData<List<User>> getAllUsers() {
        MutableLiveData<List<User>> tempLive = new MutableLiveData<>();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> temp = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()){
                    temp.add(s.getValue(User.class));
                }
                tempLive.setValue(temp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        return tempLive;
    }

    public interface UserCallBack {
        void callback(User user);
    }
}
