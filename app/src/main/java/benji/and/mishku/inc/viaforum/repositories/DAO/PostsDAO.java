package benji.and.mishku.inc.viaforum.repositories.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.models.relationships.SubforumAndPost;
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
            "SELECT * FROM Post" +
                    " JOIN User ON User.id=Post.userId"
    )
    LiveData<Map<User, List<Post>>> getPostsByUser();


    @Query(
            "SELECT * FROM Post" +
                    " JOIN Subforum ON Subforum.id=Post.subForumId"
    )
    LiveData<Map<Subforum, List<Post>>> getPostsBySubforum();
}
