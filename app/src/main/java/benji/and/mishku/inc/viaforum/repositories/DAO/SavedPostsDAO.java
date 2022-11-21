package benji.and.mishku.inc.viaforum.repositories.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.SavedPost;
@Dao
public interface SavedPostsDAO {
    @Insert
    void insert(SavedPost savedPost);

    @Update
    void update(SavedPost savedPost);

    @Delete
    void delete(SavedPost savedPost);

    @Query("SELECT p.postId,p.userId,p.subForumId,p.flag,p.flagDescription,p.postText,p.timestamp,p.title FROM SavedPost sp JOIN Post p ON sp.postId=p.postId WHERE p.userId=:userId")
    LiveData<List<Post>> getSavedPostsForUser(Long userId);

    @Query("SELECT * FROM SavedPost WHERE postId=:postId AND  userId=:userId")

    SavedPost getSavedPostIfExists(Long userId, Long postId);
}
