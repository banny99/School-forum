package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.contracts.CommentsService;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;

public class CommentsViewModel extends AndroidViewModel {
    private CommentsService commentsService;
    public CommentsViewModel(@NonNull Application application, CommentsService commentsService) {
        super(application);
        this.commentsService=commentsService;

    }
    public LiveData<List<Comment>> getCommentsForPost(Post post){
        return commentsService.getCommentsForPost(post);
    }
    public void addComment( Comment comment){
        commentsService.addComment(comment);
    }
    public void deleteComment(Comment comment){
        commentsService.deleteComment(comment);
    }
    public void updateComment(Comment comment){
        commentsService.updateComment(comment);
    }

}
