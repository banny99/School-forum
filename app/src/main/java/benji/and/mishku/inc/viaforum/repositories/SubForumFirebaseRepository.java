package benji.and.mishku.inc.viaforum.repositories;

import static android.content.ContentValues.TAG;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.SubforumsService;
import benji.and.mishku.inc.viaforum.models.Subforum;

public class SubForumFirebaseRepository  implements SubforumsService {
    private static volatile SubForumFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference subforumsRef;
    private MutableLiveData<List<Subforum>> allSubforums;

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
    public Subforum getSubforumById(String subforumId) {
        return null;
    }

}
