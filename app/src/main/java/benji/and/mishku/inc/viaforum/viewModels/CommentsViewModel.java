package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import benji.and.mishku.inc.viaforum.contracts.CommentsService;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.repositories.CommentsFirebaseRepository;

public class CommentsViewModel extends AndroidViewModel {
    private final CommentsService commentsService;



    public CommentsViewModel(@NonNull Application application) {
        super(application);
        this.commentsService= CommentsFirebaseRepository.getInstance();
    }
    public LiveData<List<Comment>> getCommentsForPost(String postId){
        return commentsService.getCommentsForPost(postId);
    }
    public int getNoOfCommentsForPost(String postId){
        List<Comment> commentsForPost=new ArrayList<>();
        commentsService.getCommentsForPost(postId).observeForever(comments -> commentsForPost.addAll(comments));
        return commentsForPost.size();
    }
    public void addComment( Comment comment){
        commentsService.addComment(comment);
    }
    public void deleteComment(String commentId){
        commentsService.deleteComment(commentId);
    }
    public void updateComment(Comment comment){
        commentsService.updateComment(comment);
    }

}
