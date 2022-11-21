package benji.and.mishku.inc.viaforum.repositories;

import static android.content.ContentValues.TAG;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.PostsService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public class PostsFirebaseRepository implements PostsService {

    private static volatile PostsFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference postsRef;
    private MutableLiveData<List<Post>> allPosts;
    private MutableLiveData<List<Post>> myPosts;

    private PostsFirebaseRepository(){
        postsRef = FirebaseDatabase.getInstance().getReference("posts");
        allPosts = new MutableLiveData<>();
        myPosts = new MutableLiveData<>();

        // Read from the database
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //update all posts:
                ArrayList<Post> tempAllPosts = new ArrayList<>();
                ArrayList<Post> tempMyPosts = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Post p = postSnapshot.getValue(Post.class);

                    tempAllPosts.add(p);
                    if (p.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        tempMyPosts.add(p);
                    }
                }
                allPosts.setValue(tempAllPosts);
                myPosts.setValue(tempMyPosts);

                Log.d(TAG, "Value is: ");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    //Todo: do we need this to be singleton/protected ? isn't getReference() thread-safe ?
    public static PostsFirebaseRepository getInstance(){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new PostsFirebaseRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void addPost(Post post) {
        post.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String postId = postsRef.push().getKey();
        post.setId(postId);
        postsRef.child(postId).setValue(post);
    }

    @Override
    public void deletePost(Post post) {
        postsRef.child(post.getId()).setValue(null);
    }

    @Override
    public void updatePost(Post post) {
        postsRef.child(post.getId()).setValue(post);
    }

    @Override
    //ToDo.. do we need this?
    public LiveData<List<Post>> getPosts(int noOfItems) {
        return null;
    }

    @Override
    public LiveData<List<Post>> getAllPosts() {
        return allPosts;
    }

    @Override
    //ToDo: ask Kasper ??
    //not really livedata anymore, or ?
    public LiveData<List<Post>> getPostsBySubforum(String subforumId) {
        MutableLiveData<List<Post>> tempLive = new MutableLiveData<>();

        Query query = postsRef.orderByChild("userId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> temp = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()){
                    temp.add(s.getValue(Post.class));
                }
                tempLive.setValue(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return tempLive;
    }

    @Override
    public LiveData<List<Post>> getPostsByUser(String userId) {
        return null;
    }


    @Override
    public Post getPostById(int id) {
        return null;
    }

    @Override
    //ToDo: I dont think we will keep this ...
    public void removeAllPosts() {
        postsRef.setValue(null);
    }

    @Override
    public LiveData<List<Post>> getAllPostsFromSubscribedSubforums(String userId) {
        return null;
    }

}