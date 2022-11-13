package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;


import javax.xml.transform.Result;

import benji.and.mishku.inc.viaforum.contracts.UserService;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.DAO.UserDAO;
//TODO Add try catches for SQLite Exceptions and handle accordingly

public class UserRepository implements UserService {
    private static volatile UserRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();
    private ExecutorService executorService;
    private UserDAO dao;
    private Handler mainThreadHandler;
    private UserRepository(Application application){
        ForumDatabase forumDatabase=ForumDatabase.getInstance(application);
        dao=forumDatabase.userDAO();
        executorService= Executors.newFixedThreadPool(5);
        mainThreadHandler= HandlerCompat.createAsync(Looper.getMainLooper());
    }
    public static UserRepository getInstance(Application application){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new UserRepository(application);
                }
            }
        }
        return instance;
    }
    //TODO define log in logic
    @Override
    public User logUserIn(String username, String password) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {


        return dao.getUserByUsername(username);
    }

    @Override
    public void addUser(User user) {
        executorService.execute(()->{
            try {
                dao.insert(user);
            }catch (SQLiteException e) {
                e.printStackTrace();

            }
        });
    }
}
