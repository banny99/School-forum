package benji.and.mishku.inc.viaforum.repositories;

import static android.content.ContentValues.TAG;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.SubforumsService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public class SubForumFirebaseRepository  implements SubforumsService {
    private static volatile SubForumFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference subforumsRef;
    private MutableLiveData<List<Subforum>> allSubforums=new MutableLiveData<>();

    private SubForumFirebaseRepository() {
        subforumsRef = FirebaseDatabase.getInstance().getReference("subforums");
        allSubforums = new MutableLiveData<>();

        // Read from the database
        subforumsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Subforum> tempSubforums = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Subforum s = postSnapshot.getValue(Subforum.class);
                    tempSubforums.add(s);
                }
                allSubforums.setValue(tempSubforums);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static SubForumFirebaseRepository getInstance(){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new SubForumFirebaseRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public LiveData<List<Subforum>> getSubforums() {
        return allSubforums;
    }

    @Override
    public void addSubforum(Subforum s) {
        String id = subforumsRef.push().getKey();
        s.setId(id);
        subforumsRef.child(id).setValue(s);
    }

    @Override
    public void updateSubforum(Subforum s) {
        subforumsRef.child(s.getId()).setValue(s);
    }

    @Override
    public void deleteSubforum(String subforumId) {
        subforumsRef.child(subforumId).setValue(null);
    }

    @Override
    public LiveData<Subforum> getSubforumById(String subforumId) {
        MutableLiveData<Subforum> subforum=new MutableLiveData<>();
        subforumsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Subforum s = postSnapshot.getValue(Subforum.class);
                    assert s != null;
                    if(Objects.equals(s.getId(), subforumId)){
                        subforum.setValue(s);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return subforum;
    }



    public interface SubforumCallback {
        void callback(Subforum subforum);
    }

}
