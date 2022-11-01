package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public interface PostsService {
    void addPost(Post post);
    void deletePost(Post post);
    void updatePost(Post post);
    LiveData<List<Post>> getPosts(int noOfItems);
    LiveData<List<Post>> getAllPosts();
    LiveData<List<Post>> getPostsBySubforum(Subforum subforum);
    LiveData<List<Post>> getPostsByUser(User user);
}
