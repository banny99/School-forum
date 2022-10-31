package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import benji.and.mishku.inc.viaforum.contracts.PostsService;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.DAO.PostsDAO;
import benji.and.mishku.inc.viaforum.repositories.DAO.SubforumsDAO;
//TODO Add try catches for SQLite Exceptions and handle accordingly

public class PostsRepository implements PostsService {
    private static volatile PostsRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();
    private ExecutorService executorService;
    private PostsDAO dao;
    private PostsRepository(Application application){
        ForumDatabase forumDatabase=ForumDatabase.getInstance(application);
        dao=forumDatabase.postsDAO();
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
            dao.insert(post);
        });
    }

    @Override
    public void deletePost(Post post) {
        executorService.execute(()->{
            dao.delete(post);
        });
    }

    @Override
    public void updatePost(Post post) {
        executorService.execute(()->{
            dao.update(post);
        });
    }

    @Override
    public LiveData<List<Post>> getPosts(int noOfItems) {
        return dao.getPostsWithLimit(noOfItems);
    }

    @Override
    public LiveData<List<Post>> getAllPosts() {
        List<Post> posts=dao.getAllPosts().getValue();
        return dao.getAllPosts();
    }

    @Override
    public LiveData<List<Post>> getPostsBySubforum(Subforum subforum) {
        LiveData<Map<Subforum, List<Post>>> temp=dao.getPostsBySubforum();
        List<Post> posts= Objects.requireNonNull(temp.getValue()).get(subforum);
        return new MutableLiveData<>(posts);
    }

    @Override
    public LiveData<List<Post>> getPostsByUser(User user) {
        LiveData<Map<User, List<Post>>> temp=dao.getPostsByUser();
        List<Post> posts= Objects.requireNonNull(temp.getValue()).get(user);
        return new MutableLiveData<>(posts);
    }
}
