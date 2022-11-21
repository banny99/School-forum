package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.contracts.SavedPostService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.repositories.SavedPostsRepository;

public class SavedPostsViewModel extends AndroidViewModel {
    private SavedPostService savedPostService;

    public SavedPostsViewModel(@NonNull Application application) {
        super(application);
        savedPostService= SavedPostsRepository.getInstance(application);
    }

    public void savePostForUser(Long userId, Long postId){
        savedPostService.savePostForUser(userId,postId);
    }
    public void unSavePostForUser(Long userId, Long postId){
        savedPostService.removeSavedPostForUser(userId,postId);
    }
    public LiveData<List<Post>> getSavedPostsForUser(Long userId){
        return savedPostService.getSavedPostsForUser(userId);
    }
    public boolean isPostSavedForUser(Long userId, Long postId){
        return savedPostService.isPostSavedByUser(userId,postId);
    }
}
