package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import benji.and.mishku.inc.viaforum.contracts.PostsService;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.repositories.PostsFirebaseRepository;

public class PostsViewModel extends AndroidViewModel {
    private final PostsService postsService;
    private Post sharedPost;
    private MutableLiveData<List<Post>> allPosts;

    private Observer<List<Post>> allPostsObserver = new Observer<List<Post>>() {
        @Override
        public void onChanged(List<Post> posts) {
            allPosts.setValue(posts);
        }
    };

    public PostsViewModel(@NonNull Application application) {
        super(application);
        postsService = PostsFirebaseRepository.getInstance();

        allPosts = new MutableLiveData<>();
        postsService.getAllPosts().observeForever(allPostsObserver);
    }

    public void addPost(Post post){
        postsService.addPost(post);
    }

    public void deletePost(String postId){
        postsService.deletePost(postId);
    }

    public LiveData<List<Post>> getAllPosts(){
        return postsService.getAllPosts();
    }

    public LiveData<List<Post>> getPostsByUser(String userId){
        return postsService.getPostsByUser(userId);
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
    public LiveData<List<Post>> getPostsBySubforum(String subforumId){
        return postsService.getPostsBySubforum(subforumId);
    }
    //TODO observe data forever here and extract posts on subscribed subforums
    public LiveData<List<Post>> getAllPostsFromSubscribedSubforums(String userId) {
        return postsService.getAllPostsFromSubscribedSubforums(userId);
    }

    public List<Post> getSearchedPosts(String searchedPhrase) {
        ArrayList<Post> searchedPosts = new ArrayList<>();
        for (Post p : Objects.requireNonNull(allPosts.getValue())){
            if (p.getTitle().toLowerCase().contains(searchedPhrase.toLowerCase()) || p.getPostText().toLowerCase().contains(searchedPhrase.toLowerCase()))
                searchedPosts.add(p);
        }
        return searchedPosts;
    }

    public void removeObserver(){
        postsService.getAllPosts().removeObserver(allPostsObserver);
    }

    public LiveData<List<Post>> getReportedPosts(){
        return postsService.getReportedPosts();
    }

    public Post getPostById(String postId){
        for (Post p: Objects.requireNonNull(allPosts.getValue())) {
            if(p.getId().equals(postId)){
                return p;
            }
        }
        return null;
    }
}
