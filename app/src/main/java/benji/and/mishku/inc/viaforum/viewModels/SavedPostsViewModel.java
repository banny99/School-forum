package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.contracts.SavedPostService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.SavedPostsFirebaseRepository;

public class SavedPostsViewModel extends AndroidViewModel {
    private SavedPostService savedPostService;

    public SavedPostsViewModel(@NonNull Application application) {
        super(application);
        savedPostService= SavedPostsFirebaseRepository.getInstance();
    }

    public void savePostForUser(User user, Post post){
        savedPostService.savePostForUser(user,post);
    }
    public void unSavePostForUser(User user,Post post){
        savedPostService.removeSavedPostForUser(user, post);
    }
    public LiveData<List<Post>> getSavedPostsForUser(User user){
        return savedPostService.getSavedPostsForUser(user);
    }
    public boolean isPostSavedForUser(User user, Post post){

        return savedPostService.isPostSavedForUser(user,post);
    }
}
