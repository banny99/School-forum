package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;

public interface CommentsService {
    void addComment(Comment c);
    void deleteComment(Comment c);
    void updateComment(Comment c);
    LiveData<List<Comment>> getCommentsForPost(Post post);
}
