package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.models.Comment;

public interface CommentsService {
    void addComment(Comment c);
    void deleteComment(String commentId);
    void updateComment(Comment c);
    LiveData<List<Comment>> getCommentsForPost(String postId);
}
