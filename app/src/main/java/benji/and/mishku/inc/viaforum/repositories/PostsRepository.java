package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import benji.and.mishku.inc.viaforum.contracts.PostsService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.repositories.DAO.PostsDAO;
import benji.and.mishku.inc.viaforum.repositories.DAO.SubscriptionsDAO;
//TODO Add try catches for SQLite Exceptions and handle accordingly

public class PostsRepository implements PostsService {
    private static volatile PostsRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();
    private ExecutorService executorService;
    private PostsDAO postsDAO;
    private SubscriptionsDAO subscriptionsDAO;

    private PostsRepository(Application application){
        ForumDatabase forumDatabase=ForumDatabase.getInstance(application);
        postsDAO =forumDatabase.postsDAO();
        subscriptionsDAO=forumDatabase.subscriptionsDAO();
        executorService= Executors.newFixedThreadPool(5);
    }

    public static PostsRepository getInstance(Application application){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new PostsRepository(application);
                }
            }
        }
        return instance;
    }
    @Override
    public void addPost(Post post) {
        executorService.execute(()->{
            postsDAO.insert(post);
        });
    }

    @Override
    public void deletePost(Post post) {
        executorService.execute(()->{
            postsDAO.delete(post);
        });
    }

    @Override
    public void updatePost(Post post) {
        executorService.execute(()->{
            postsDAO.update(post);
        });
    }

    @Override
    public LiveData<List<Post>> getPosts(int noOfItems) {
        return postsDAO.getPostsWithLimit(noOfItems);
    }

    @Override
    public LiveData<List<Post>> getAllPosts() {
        return postsDAO.getAllPosts();
    }

    @Override
    public LiveData<List<Post>> getPostsBySubforum(Long subforumId) {
        return postsDAO.getPostsBySubforum(subforumId);
    }

    @Override
    public LiveData<List<Post>> getPostsByUser(Long userId) {
        return postsDAO.getPostsByUser(userId);
    }

    @Override
    public Post getPostById(int id) {
        return postsDAO.getPostById(id);
    }

    @Override
    public void removeAllPosts() {
        executorService.execute(() -> {
            postsDAO.deleteAll();
        });
    }

    @Override
    public LiveData<List<Post>> getAllPostsFromSubscribedSubforums(Long userId) {
        return postsDAO.getAllPostsFromSubscribedSubforums(userId);
    }
}
