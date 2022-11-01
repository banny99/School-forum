package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import benji.and.mishku.inc.viaforum.contracts.PostsService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.repositories.PostsRepository;

public class PostsViewModel extends AndroidViewModel {
    private PostsService postsService;

    public PostsViewModel(@NonNull Application application) {
        super(application);
        postsService= PostsRepository.getInstance(application);
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

    public void removeAllPosts() {
        postsService.removeAllPosts();
    }
}
