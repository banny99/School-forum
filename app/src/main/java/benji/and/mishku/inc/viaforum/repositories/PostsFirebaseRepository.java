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
import benji.and.mishku.inc.viaforum.contracts.PostsService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public class PostsFirebaseRepository implements PostsService {

    private static volatile PostsFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference postsRef;
    private MutableLiveData<List<Post>> posts;

    public void setPosts(ArrayList<Post> posts){
        this.posts.setValue(posts);
    }

    private PostsFirebaseRepository(){
        postsRef = FirebaseDatabase.getInstance().getReference("posts");
        posts = new MutableLiveData<>();

        // Read from the database
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<Post> allPosts = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    allPosts.add(postSnapshot.getValue(Post.class));
                }
                setPosts(allPosts);
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
        post.setId((long)-1);
        postsRef.child(post.getId().toString()).setValue(post);
    }

    @Override
    public void deletePost(Post post) {
        postsRef.child(post.getId().toString()).setValue(null);
    }

    @Override
    public void updatePost(Post post) {
        postsRef.child(post.getId().toString()).setValue(post);
    }

    @Override
    public LiveData<List<Post>> getPosts(int noOfItems) {
        return null;
    }

    @Override
    public LiveData<List<Post>> getAllPosts() {
        return posts;
    }

    @Override
    public LiveData<List<Post>> getPostsBySubforum(Subforum subforum) {
        return null;
    }

    @Override
    public LiveData<List<Post>> getPostsByUser(User user) {
        return null;
    }

    @Override
    public Post getPostById(int id) {
        return null;
    }

    @Override
    public void removeAllPosts() {

    }
}
