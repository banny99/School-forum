package benji.and.mishku.inc.viaforum.repositories;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import benji.and.mishku.inc.viaforum.contracts.CommentsService;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;

public class CommentsFirebaseRepository implements CommentsService {

    private static volatile CommentsFirebaseRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();

    private final DatabaseReference commentsRef;
    private MutableLiveData<List<Comment>> allComments;

    private CommentsFirebaseRepository() {
        commentsRef = FirebaseDatabase.getInstance().getReference("comments");
    }

    public static CommentsService getInstance(){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new CommentsFirebaseRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void addComment(Comment c) {
        String id = commentsRef.push().getKey();
        c.setId(id);
        commentsRef.child(id).setValue(c);
    }

    @Override
    public void deleteComment(String commentId) {
        commentsRef.child(commentId).setValue(null);
    }

    @Override
    public void updateComment(Comment c) {
        commentsRef.child(c.getId()).setValue(c);
    }

    @Override
    public LiveData<List<Comment>> getCommentsForPost(String postId) {

        MutableLiveData<List<Comment>> tempLive = new MutableLiveData<>();

        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Comment> temp = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()){
                    if(Objects.requireNonNull(s.getValue(Comment.class)).getPostId().equals(postId)){
                        temp.add(s.getValue(Comment.class));
                    }
                }
                tempLive.setValue(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return tempLive;
    }
}
