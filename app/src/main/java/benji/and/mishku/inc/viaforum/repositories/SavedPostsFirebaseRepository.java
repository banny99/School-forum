package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import benji.and.mishku.inc.viaforum.contracts.SavedPostService;
import benji.and.mishku.inc.viaforum.models.Post;

public class SavedPostsFirebaseRepository implements SavedPostService {

    private static volatile SavedPostsFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference savedPostsRef;

    private SavedPostsFirebaseRepository(){
        savedPostsRef = FirebaseDatabase.getInstance().getReference("savedPosts");
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
    public LiveData<List<Post>> getSavedPostsForUser(String userId) {
        return null;
    }

    @Override
    public void savePostForUser(String userId, String postId) {

    }

    @Override
    public void removeSavedPostForUser(String userId, String postId) {

    }

    @Override
    public boolean isPostSavedByUser(String userId, String postId) {
        return false;
    }
}
