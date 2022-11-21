package benji.and.mishku.inc.viaforum.repositories.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;


@Dao
public interface UserDAO {
    @Insert
    void insert(User subforum);

    @Update
    void update(User subforum);

    @Delete
    void delete(User subforum);

    @Query("SELECT * FROM User WHERE username= :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM User WHERE id= :userId")
    User getUserById(Long userId);

}
