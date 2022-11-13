//package benji.and.mishku.inc.viaforum.repositories;
//
//import android.app.Application;
//
//import androidx.lifecycle.LiveData;
//
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.locks.ReentrantLock;
//
//import benji.and.mishku.inc.viaforum.contracts.SubforumsService;
//import benji.and.mishku.inc.viaforum.models.Subforum;
//import benji.and.mishku.inc.viaforum.repositories.DAO.SubforumsDAO;
//import benji.and.mishku.inc.viaforum.repositories.DAO.UserDAO;
////TODO Add try catches for SQLite Exceptions and handle accordingly
//public class SubforumsRepository implements SubforumsService {
//    private static volatile SubforumsRepository instance;
//    private final static ReentrantLock lock=new ReentrantLock();
//    private ExecutorService executorService;
//    private SubforumsDAO dao;
//    private SubforumsRepository(Application application){
//        ForumDatabase forumDatabase=ForumDatabase.getInstance(application);
//        dao=forumDatabase.subforumsDAO();
//        executorService= Executors.newFixedThreadPool(10);
//
//    }
//    public static SubforumsRepository getInstance(Application application){
//        if(instance==null){
//            synchronized (lock){
//                if(instance==null) {
//                    instance = new SubforumsRepository(application);
//                }
//            }
//        }
//        return instance;
//    }
//
//    @Override
//    public LiveData<List<Subforum>> getSubforums() {
//        return dao.getSubforums();
//    }
//
//    @Override
//    public void addSubforum(Subforum s) {
//        executorService.execute(()->{
//            dao.insert(s);
//        });
//    }
//
//    @Override
//    public void updateSubforum(Subforum s) {
//        executorService.execute(()->{
//            dao.update(s);
//        });
//    }
//
//    @Override
//    public void deleteSubforum(Subforum s) {
//        executorService.execute(()->{
//            dao.delete(s);
//        });
//    }
//
//    @Override
//    public void subscribeToSubforum(Long userId, Long subforumId) {
//
//    }
//
//
//}
