package benji.and.mishku.inc.viaforum.repositories.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.MapInfo;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;

@Dao
public interface CommentsDAO {
    @Insert
    void insert(Comment comment);

    @Update
    void update(Comment comment);

    @Delete
    void delete(Comment comment);

    @Query(
            "SELECT c.id, c.postId, c.userId, c.content FROM Post" +
                    " JOIN Comment c ON Post.postId = c.postId WHERE Post.postId=:postId"
    )
    LiveData<List<Comment>> getCommentsForPost(Long postId);

}
