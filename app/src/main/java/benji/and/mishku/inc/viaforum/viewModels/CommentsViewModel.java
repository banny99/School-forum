package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.contracts.CommentsService;
import benji.and.mishku.inc.viaforum.models.Comment;
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
