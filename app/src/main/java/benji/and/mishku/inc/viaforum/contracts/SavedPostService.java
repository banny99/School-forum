package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;

public interface SavedPostService {
    LiveData<List<Post>> getSavedPostsForUser(User user);
    void savePostForUser(User user, Post post);
    void removeSavedPostForUser(User user, Post post);
    boolean isPostSavedByUser(User user, Post post);
}
