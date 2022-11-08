package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;


public class YourPostsFragment extends Fragment {

    private PostsViewModel postsViewModel;
    private RecyclerView postListRV;
    private PostAdapter postAdapter;
    private Button removeAllPostsButton;

    public YourPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postAdapter = new PostAdapter(new ArrayList<>());

        postsViewModel=new ViewModelProvider(getActivity()).get(PostsViewModel.class);
        postsViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postAdapter.setPosts(posts);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_your_posts, container, false);

        //Initialize objects ...
        postListRV = inflatedView.findViewById(R.id.rv);
        postListRV.hasFixedSize();
        postListRV.setLayoutManager(new LinearLayoutManager(getContext()));

        //on list-item clicked
        postAdapter.setOnClickListener(post -> {
            postsViewModel.setSharedPost(post);
            Navigation.findNavController(inflatedView).navigate(R.id.nav_post_detail);
        });
        postListRV.setAdapter(postAdapter);

        removeAllPostsButton = inflatedView.findViewById(R.id.removeButton);
        removeAllPostsButton.setOnClickListener(view -> {
            postsViewModel.removeAllPosts();
        });

        return inflatedView;
    }
}