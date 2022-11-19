package benji.and.mishku.inc.viaforum.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import benji.and.mishku.inc.viaforum.contracts.CommentsService;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;

public class CommentsFirebaseRepository implements CommentsService {

    private static volatile CommentsFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference commentsRef;
    private MutableLiveData<List<Comment>> allComments;
    private float maxId = 0;

    public CommentsFirebaseRepository() {
        commentsRef = FirebaseDatabase.getInstance().getReference("comments");
    }

    @Override
    public void addComment(Comment c) {
        String id = commentsRef.push().getKey();
        c.setId(id);
        commentsRef.child(id).setValue(c);
    }

    @Override
    public void deleteComment(Comment c) {
        commentsRef.child(c.getId().toString()).setValue(null);
    }

    @Override
    public void updateComment(Comment c) {
        commentsRef.child(c.getId().toString()).setValue(c);
    }

    @Override
    public LiveData<List<Comment>> getCommentsForPost(Post post) {
        ArrayList<Comment> comments = new ArrayList<>();

        commentsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    for (DataSnapshot s : task.getResult().getChildren()){
                        Comment c = s.getValue(Comment.class);
                        if (c.getPostId().equals(post.getId())){
                            comments.add(c);
                        }
                    }
                }
            }
        });

        MutableLiveData<List<Comment>> liveData = new MutableLiveData<>();
        liveData.setValue(comments);
        return liveData;
    }
}
