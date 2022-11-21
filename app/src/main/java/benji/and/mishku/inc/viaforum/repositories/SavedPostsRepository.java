package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import benji.and.mishku.inc.viaforum.contracts.SavedPostService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.SavedPost;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.DAO.SavedPostsDAO;

public class SavedPostsRepository implements SavedPostService {
    private static volatile SavedPostsRepository instance;
    private ExecutorService executorService;
    private final static ReentrantLock lock=new ReentrantLock();
    private SavedPostsDAO savedPostsDAO;

    private SavedPostsRepository(Application application){
        ForumDatabase forumDatabase=ForumDatabase.getInstance(application);
        savedPostsDAO =forumDatabase.savedPostsDAO();

        executorService= Executors.newFixedThreadPool(5);
    }

    public static SavedPostsRepository getInstance(Application application){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new SavedPostsRepository(application);
                }
            }
        }
        return instance;
    }
    @Override
    public LiveData<List<Post>> getSavedPostsForUser(Long userId) {
        return savedPostsDAO.getSavedPostsForUser(userId);
    }

    @Override
    public void savePostForUser(Long userId, Long postId) {
        executorService.execute(()->{

            savedPostsDAO.insert(new SavedPost(userId,postId));
        });
    }

    @Override
    public void removeSavedPostForUser(Long userId, Long postId) {
        executorService.execute(()->{
            savedPostsDAO.delete(new SavedPost(userId,postId));
        });
    }

    @Override
    public boolean isPostSavedByUser(Long userId, Long postId) {
        return savedPostsDAO.getSavedPostIfExists(userId, postId) != null;

    }
}
