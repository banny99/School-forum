package benji.and.mishku.inc.viaforum.views.fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.viewModels.CommentsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SavedPostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubscriptionsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;
import utils.PostAdapter;


public class SubforumPostsFragment extends Fragment {

   private SubforumsViewModel subforumsViewModel;
    private RecyclerView postListRV;
    private PostAdapter postAdapter;
    private PostsViewModel postsViewModel;
    private UserViewModel userViewModel;
    private SubscriptionsViewModel subscriptionsViewModel;
    private SavedPostsViewModel savedPostsViewModel;
    private CommentsViewModel commentsViewModel;
    private Subforum currentSubforum;
    private Button subscribeButton;
    private TextView title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postsViewModel=new ViewModelProvider(requireActivity()).get(PostsViewModel.class);
        subforumsViewModel=new ViewModelProvider(requireActivity()).get(SubforumsViewModel.class);
        subscriptionsViewModel=new ViewModelProvider(requireActivity()).get(SubscriptionsViewModel.class);
        savedPostsViewModel=new ViewModelProvider(requireActivity()).get(SavedPostsViewModel.class);
        userViewModel=new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        commentsViewModel=new ViewModelProvider(requireActivity()).get(CommentsViewModel.class);
        postAdapter = new PostAdapter(new ArrayList<>(),subforumsViewModel,savedPostsViewModel,userViewModel,commentsViewModel);
        currentSubforum=subforumsViewModel.getSharedSubforum();
        postsViewModel.getPostsBySubforum(currentSubforum.getId()).observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postAdapter.setPosts(posts);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_subforum_posts, container, false);
        postListRV = view.findViewById(R.id.rvPostsOnSubforum);
        postListRV.hasFixedSize();
        postListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        title=view.findViewById(R.id.subForumSingleName);
        title.setText(currentSubforum.getName());
        if(userViewModel.getLoggedUser().getValue()!=null) {
            subscribeButton = view.findViewById(R.id.subscribeButton);
            subscribeButton.setOnClickListener((l) ->
            {
                subscriptionsViewModel.subscribeToSubforum(userViewModel.getLoggedUser().getValue(), currentSubforum);
                CharSequence text = "You just subscribed to " + currentSubforum.getName() + " subforum";
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            });
            postAdapter.setOnClickListener(post -> {
                postsViewModel.setSharedPost(post);
                Navigation.findNavController(view).navigate(R.id.nav_post_detail);
            });
            if(Objects.equals(userViewModel.getLoggedUser().getValue().getEmail(), "admin@forumvia.dk")){
                subscribeButton.setVisibility(View.GONE);
            }
        }

        postListRV.setAdapter(postAdapter);
        return  view;
    }
}