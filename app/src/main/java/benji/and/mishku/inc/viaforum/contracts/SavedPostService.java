package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;

public interface SavedPostService {
    LiveData<List<Post>> getSavedPostsForUser(Long userId);
    void savePostForUser(Long userId, Long postId);
    void removeSavedPostForUser(Long userId, Long postId);
    boolean isPostSavedByUser(Long userId, Long postId);
}
