package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.contracts.PostsService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.repositories.PostsFirebaseRepository;

public class PostsViewModel extends AndroidViewModel {
    private final PostsService postsService;
    private Post sharedPost;

    public PostsViewModel(@NonNull Application application) {
        super(application);
        postsService = PostsFirebaseRepository.getInstance();
    }
    public void addPost(Post post){
        postsService.addPost(post);
    }
    public void deletePost(Post post){
        postsService.deletePost(post);
    }
    public LiveData<List<Post>> getAllPosts(){
        return postsService.getAllPosts();
    }
    public Post getPostById(int id){
        return postsService.getPostById(id);
    }

    public Post getSharedPost() {
        return sharedPost;
    }
    public void setSharedPost(Post sharedPost) {
        this.sharedPost = sharedPost;
    }

    public void removeAllPosts() {
        postsService.removeAllPosts();
    }

    public void updatePost(Post editedPost) {
        postsService.updatePost(editedPost);
    }

    public LiveData<List<Post>> getPostBySubforum(Subforum subforum){
        return postsService.getPostsBySubforum(subforum);
    }
}
