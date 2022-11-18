package benji.and.mishku.inc.viaforum.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import benji.and.mishku.inc.viaforum.contracts.SubscriptionService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.Subscription;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.DAO.PostsDAO;
import benji.and.mishku.inc.viaforum.repositories.DAO.SubscriptionsDAO;

public class SubscriptionsRepository implements SubscriptionService {
    private static volatile SubscriptionsRepository instance;
    private final static ReentrantLock lock=new ReentrantLock();
    private ExecutorService executorService;
    private SubscriptionsDAO dao;
    public static SubscriptionsRepository getInstance(Application application){
        if(instance==null){
            synchronized (lock){
                if(instance==null) {
                    instance = new SubscriptionsRepository(application);
                }
            }
        }
        return instance;
    }

    public SubscriptionsRepository(Application application) {
        dao = ForumDatabase.getInstance(application.getApplicationContext()).subscriptionsDAO();
        executorService= Executors.newFixedThreadPool(5);
    }

    @Override
    public LiveData<List<Subforum>> getSubscriptionsForUser(Long userId) {
        return dao.getSubscriptionsForUser(userId);
    }

    @Override
    public LiveData<List<User>> getUsersSubscribedToSubforum(Long subId) {
        return dao.getUsersForSubforum(subId);
    }

    @Override
    public void subscribeUserToSubforum(Long userId, Long subforumId) {
        executorService.execute(()->{
            Subscription subscription=new Subscription();
            subscription.setUserId(userId);
            subscription.setSubforumId(subforumId);
            dao.insert(subscription);
        });


    }

    @Override
    public void unSubscribeUserFromSubforum(Long userId, Long subforumId) {
        executorService.execute(()->{
            Subscription subscription=new Subscription();
            subscription.setUserId(userId);
            subscription.setSubforumId(subforumId);
            dao.delete(subscription);
        });
    }
}
