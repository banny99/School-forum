package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.contracts.SavedPostService;
import benji.and.mishku.inc.viaforum.models.Post;

public class SavedPostsFirebaseRepository implements SavedPostService {

    public static SavedPostService getInstance(Application application) {
        //ToDo: ..do we need this to be singleton ? if yes do so for all Repos
    }

    //ToDo: implement with firebase

    @Override
    public LiveData<List<Post>> getSavedPostsForUser(Long userId) {
        return null;
    }

    @Override
    public void savePostForUser(Long userId, Long postId) {

    }

    @Override
    public void removeSavedPostForUser(Long userId, Long postId) {

    }

    @Override
    public boolean isPostSavedByUser(Long userId, Long postId) {
        return false;
    }
}
