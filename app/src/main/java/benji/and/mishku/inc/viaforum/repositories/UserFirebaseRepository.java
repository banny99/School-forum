package benji.and.mishku.inc.viaforum.repositories;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.UserService;
import benji.and.mishku.inc.viaforum.models.Post;
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
    public User logUserIn(String username, String password) {
        return null;
    }

    private User loggedUser;
    @Override
    public User getUserByUsername(String username) {
        // Read from the database
        usersRef.child("-1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                loggedUser = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Value is: "+loggedUser.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return loggedUser;
    }

    @Override
    public void addUser(User user) {
        user.setId((long)-1);
        usersRef.child(user.getId().toString()).setValue(user);
    }
}
