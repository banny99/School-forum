package benji.and.mishku.inc.viaforum.repositories.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
@Dao
public interface SubforumsDAO {
    @Insert
    void insert(Subforum subforum);

    @Update
    void update(Subforum subforum);

    @Delete
    void delete(Subforum subforum);

    @Query("SELECT * FROM Subforum")
    LiveData<List<Subforum>> getSubforums();
    @Query("SELECT * FROM Subforum WHERE id=:subforumId")
    Subforum getSubforumById(Long subforumId);
}
