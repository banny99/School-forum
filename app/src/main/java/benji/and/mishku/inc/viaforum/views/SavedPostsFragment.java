package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SavedPostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class SavedPostsFragment extends Fragment {
    private PostsViewModel postsViewModel;
    private SubforumsViewModel subforumsViewModel;
    private SavedPostsViewModel savedPostsViewModel;
    private UserViewModel userViewModel;
    private RecyclerView postListRV;
    private PostAdapter postAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postsViewModel=new ViewModelProvider(requireActivity()).get(PostsViewModel.class);
        subforumsViewModel=new ViewModelProvider(requireActivity()).get(SubforumsViewModel.class);
        savedPostsViewModel=new ViewModelProvider(requireActivity()).get(SavedPostsViewModel.class);
        userViewModel=new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        postAdapter = new PostAdapter(new ArrayList<>(),subforumsViewModel,savedPostsViewModel,userViewModel);


        savedPostsViewModel.getSavedPostsForUser(userViewModel.getLoggedUser().getValue()).observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postAdapter.setPosts(posts);
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView=inflater.inflate(R.layout.fragment_saved_posts, container, false);
        //Initialize objects ...
        postListRV = inflatedView.findViewById(R.id.rvSavedPosts);
        postListRV.hasFixedSize();
        postListRV.setLayoutManager(new LinearLayoutManager(getContext()));

        //on list-item clicked
        postAdapter.setOnClickListener(post -> {
            postsViewModel.setSharedPost(post);
            Navigation.findNavController(inflatedView).navigate(R.id.nav_post_detail);
        });
        postListRV.setAdapter(postAdapter);
        return inflatedView;
    }
}
