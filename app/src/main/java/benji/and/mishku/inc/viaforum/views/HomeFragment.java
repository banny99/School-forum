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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SavedPostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class HomeFragment extends Fragment {
    private PostsViewModel postsViewModel;
    private UserViewModel userViewModel;
    private SubforumsViewModel subforumsViewModel;
    private SavedPostsViewModel savedPostsViewModel;
    private RecyclerView postListRV;
    private PostAdapter postAdapter;

    private FloatingActionButton floatingSearchButton;

    private LinearLayout searchBar;
    private EditText searchInput;
    private ImageButton searchBtn;
    private Button moreSearchOptionsBtn;
    private boolean isSearching = false;

    private LinearLayout sortBar;
    private boolean isSorting = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postsViewModel=new ViewModelProvider(requireActivity()).get(PostsViewModel.class);
        subforumsViewModel=new ViewModelProvider(requireActivity()).get(SubforumsViewModel.class);
        savedPostsViewModel=new ViewModelProvider(requireActivity()).get(SavedPostsViewModel.class);
        userViewModel=new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        postAdapter = new PostAdapter(new ArrayList<>(),subforumsViewModel,savedPostsViewModel,userViewModel);
        postsViewModel.getAllPostsFromSubscribedSubforums(FirebaseAuth.getInstance().getCurrentUser().getUid()).observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                if (!isSearching)
                    postAdapter.setPosts(posts);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView=inflater.inflate(R.layout.fragment_home, container, false);

        //Initialize objects ...
        searchBar = inflatedView.findViewById(R.id.search_bar);
        searchInput = inflatedView.findViewById(R.id.search_input);
        searchBtn = inflatedView.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(view -> {
            isSearching = true;
            String searchedPhrase = searchInput.getText().toString();
            if (!searchedPhrase.isEmpty())
                postAdapter.setPosts(postsViewModel.getSearchedPosts(searchedPhrase));
        });
        floatingSearchButton = inflatedView.findViewById(R.id.floating_search_btn);
        floatingSearchButton.setOnClickListener(view -> {
            if (searchBar.getVisibility() == View.VISIBLE){
                //clear searchBar
                searchInput.setText("");
                searchBar.setVisibility(View.GONE);
                //clear sortBar
                sortBar.setVisibility(View.GONE);

                //set isSearching to false ->so the post-list shows all posts again
                isSearching = false;
                postAdapter.setPosts(postsViewModel.getAllPosts().getValue());
                //change icon
                floatingSearchButton.setImageResource(R.drawable.ic_baseline_search_24);

            }
            else{
                floatingSearchButton.setImageResource(R.drawable.ic_baseline_clear_24);
                searchBar.setVisibility(View.VISIBLE);
            }
        });

        sortBar = inflatedView.findViewById(R.id.sort_bar);
        moreSearchOptionsBtn = inflatedView.findViewById(R.id.more_options_btn);
        moreSearchOptionsBtn.setOnClickListener(view -> {
            if (sortBar.getVisibility() == View.VISIBLE){
                sortBar.setVisibility(View.GONE);
                moreSearchOptionsBtn.setText("---^---");
            }
            else{
                sortBar.setVisibility(View.VISIBLE);
                moreSearchOptionsBtn.setText("---˅---");
            }
        });


        postListRV = inflatedView.findViewById(R.id.rvHome);
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