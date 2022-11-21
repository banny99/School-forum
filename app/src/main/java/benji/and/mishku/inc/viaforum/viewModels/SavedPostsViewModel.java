package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.contracts.SavedPostService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.repositories.SavedPostsFirebaseRepository;

public class SavedPostsViewModel extends AndroidViewModel {
    private SavedPostService savedPostService;

    public SavedPostsViewModel(@NonNull Application application) {
        super(application);
        savedPostService= SavedPostsFirebaseRepository.getInstance();
    }

    public void savePostForUser(String userId, String postId){
        savedPostService.savePostForUser(userId,postId);
    }
    public void unSavePostForUser(String userId, String postId){
        savedPostService.removeSavedPostForUser(userId,postId);
    }
    public LiveData<List<Post>> getSavedPostsForUser(String userId){
        return savedPostService.getSavedPostsForUser(userId);
    }
    public boolean isPostSavedForUser(String userId, String postId){
        return savedPostService.isPostSavedByUser(userId,postId);
    }
}
