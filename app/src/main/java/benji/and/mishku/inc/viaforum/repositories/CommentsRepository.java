package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataKt;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import benji.and.mishku.inc.viaforum.contracts.CommentsService;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.repositories.DAO.CommentsDAO;
import benji.and.mishku.inc.viaforum.repositories.DAO.PostsDAO;

//TODO Add try catches for SQLite Exceptions and handle accordingly

public class CommentsRepository implements CommentsService {
    private static volatile CommentsRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();
    private final ExecutorService executorService;
    private CommentsDAO dao;
    private CommentsRepository(Application application){
        ForumDatabase forumDatabase=ForumDatabase.getInstance(application);
        dao=forumDatabase.commentsDAO();
        executorService= Executors.newFixedThreadPool(5);
    }
    public static CommentsRepository getInstance(Application application){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new CommentsRepository(application);
                }
            }
        }
        return instance;
    }
    @Override
    public void addComment(Comment c) {
        try {
            executorService.execute(() -> {
                dao.insert(c);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteComment(Comment c) {
        try {
            executorService.execute(() -> {
                dao.delete(c);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateComment(Comment c) {
        try{
            executorService.execute(()->{
                dao.update(c);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public LiveData<List<Comment>> getCommentsForPost(Post p) {
        LiveData<Map<Post, List<Comment>>> temp=dao.getCommentsForPost();
        List<Comment> comments= Objects.requireNonNull(temp.getValue()).get(p);
        return new MutableLiveData<>(comments);
    }
}
