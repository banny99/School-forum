package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import java.util.ArrayList;
import benji.and.mishku.inc.viaforum.R;
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

    private LinearLayout searchBar;
    private EditText searchInput;
    private ImageButton searchBtn;
    private FloatingActionButton floatingSearchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        postsViewModel=new ViewModelProvider(requireActivity()).get(PostsViewModel.class);
        subforumsViewModel=new ViewModelProvider(requireActivity()).get(SubforumsViewModel.class);
        savedPostsViewModel=new ViewModelProvider(requireActivity()).get(SavedPostsViewModel.class);
        postAdapter = new PostAdapter(new ArrayList<>(),subforumsViewModel,savedPostsViewModel);
//        postsViewModel.getAllPostsFromSubscribedSubforums(userViewModel.getLoggedUser().getValue().getUserId()).observe(this, new Observer<List<Post>>() {
//            @Override
//            public void onChanged(List<Post> posts) {
//                postAdapter.setPosts(posts);
//            }
//        });
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
            String searchedPhrase = searchInput.getText().toString();
            if (!searchedPhrase.isEmpty())
                postsViewModel.getSearchedPosts(searchInput.getText().toString());
        });
        floatingSearchButton = inflatedView.findViewById(R.id.floating_search_btn);
        floatingSearchButton.setOnClickListener(view -> {
            if (searchBar.getVisibility() == View.VISIBLE){
                floatingSearchButton.setImageResource(R.drawable.ic_baseline_search_24);
                searchInput.setText("");
                searchBar.setVisibility(View.GONE);
            }
            else{
                floatingSearchButton.setImageResource(R.drawable.ic_baseline_delete_24);
                searchBar.setVisibility(View.VISIBLE);
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