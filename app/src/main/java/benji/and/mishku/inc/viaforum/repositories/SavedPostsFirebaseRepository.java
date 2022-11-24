package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

import benji.and.mishku.inc.viaforum.contracts.SavedPostService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;

public class SavedPostsFirebaseRepository implements SavedPostService {

    private static volatile SavedPostsFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference usersRef;

    private SavedPostsFirebaseRepository(){
        usersRef = FirebaseDatabase.getInstance().getReference("users");
    }

    public static SavedPostService getInstance(){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new SavedPostsFirebaseRepository();
                }
            }
        }
        return instance;
    }

    //ToDo: implement with firebase

    @Override
    public LiveData<List<Post>> getSavedPostsForUser(User user) {
        MutableLiveData<List<Post>> savedPosts=new MutableLiveData<>();
        usersRef.child(user.getUserId()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               savedPosts.setValue(Objects.requireNonNull(snapshot.getValue(User.class)).getSavedPosts());
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

        return savedPosts;
    }

    @Override
    public void savePostForUser(User user, Post post) {

            user.getSavedPosts().add(post);
            usersRef.child(user.getUserId()).setValue(user);

    }

    @Override
    public void removeSavedPostForUser(User user, Post post) {
            user.getSavedPosts().remove(post);
            usersRef.child(user.getUserId()).setValue(user);

    }

    @Override
    public boolean isPostSavedForUser(User user, Post post) {
        final boolean[] isSaved = new boolean[1];
        usersRef.child(user.getUserId()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
              isSaved[0] = Objects.requireNonNull(snapshot.getValue(User.class)).getSavedPosts().contains(post);
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
        return isSaved[0];
    }


}
