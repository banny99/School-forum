package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.models.Post;

public interface SavedPostService {
    LiveData<List<Post>> getSavedPostsForUser(String userId);
    void savePostForUser(String userId, String postId);
    void removeSavedPostForUser(String userId, String postId);
    boolean isPostSavedByUser(String userId, String postId);
}
