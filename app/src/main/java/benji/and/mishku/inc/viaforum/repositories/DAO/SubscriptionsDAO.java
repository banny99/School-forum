package benji.and.mishku.inc.viaforum.repositories.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.Subscription;
import benji.and.mishku.inc.viaforum.models.User;

@Dao
public interface SubscriptionsDAO {
    @Insert
    void insert(Subscription subscription);
    @Delete
    void delete(Subscription subscription);
    @Update
    void update(Subscription subscription);
    @Query("SELECT sub.id, sub.description, sub.name  FROM SUBFORUM sub JOIN SUBSCRIPTION ON sub.ID=SUBSCRIPTION.subforumId WHERE SUBSCRIPTION.userId = :subscribedUserId ")
    LiveData<List<Subforum>> getSubscriptionsForUser(Long subscribedUserId);

    @Query("SELECT u.id,u.subForumId,u.birthday,u.email,u.password  FROM User u  JOIN Subscription  ON u.ID=subscription.userId WHERE SUBSCRIPTION.subforumId = :subforumId ")
    LiveData<List<User>> getUsersForSubforum(Long subforumId);
}
