package benji.and.mishku.inc.viaforum.repositories.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import java.util.Map;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

@Dao
public interface PostsDAO {
    @Insert
    void insert(Post post);

    @Update
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM Post")
    LiveData<List<Post>> getAllPosts();

    @Query("SELECT * FROM POST LIMIT :noOfItems")
    LiveData<List<Post>> getPostsWithLimit(int noOfItems);

    @Query(
            "SELECT p.postId,p.userId,p.subForumId,p.flag,p.flagDescription,p.postText,p.timestamp,p.title FROM Post p" +
                    " JOIN User u ON u.id=p.userId WHERE u.id=:userId"
    )
    LiveData<List<Post>> getPostsByUser(Long userId);


    @Query(
            "SELECT p.postId,p.userId,p.subForumId,p.flag,p.flagDescription,p.postText,p.timestamp,p.title FROM Post p" +
                    " JOIN Subforum ON Subforum.id=p.subForumId WHERE p.subForumId= :subforumId"
    )
    LiveData< List<Post>> getPostsBySubforum(Long subforumId);

    @Query(
            "SELECT * FROM Post WHERE postId=:id"
    )
    Post getPostById(int id);

    @Query("DELETE FROM Post")
    void deleteAll();
    @Query("SELECT p.postId,p.userId,p.subForumId,p.flag,p.flagDescription,p.postText,p.timestamp,p.title FROM POST p"+
    " JOIN Subscription s on p.subForumId=s.subforumId WHERE s.userId=:userId")
    LiveData<List<Post>> getAllPostsFromSubscribedSubforums(Long userId);
}
