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
import android.widget.Toast;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;


public class AddPostFragment extends Fragment {


    private PostsViewModel postsViewModel;
    private EditText postTitle;
    private EditText postText;
    private Button addPostBtn;

    public AddPostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postsViewModel=new ViewModelProvider(requireActivity()).get(PostsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_add_post, container, false);

        //Initialize objects ...
        postTitle = inflatedView.findViewById(R.id.newPostTitle);
        postText = inflatedView.findViewById(R.id.newPostText);
        addPostBtn = inflatedView.findViewById(R.id.addNewPostButton);
        addPostBtn.setOnClickListener(view -> {
            //ToDo: replace IDs with real IDs
            Post newPost = new Post(postTitle.getText().toString(), postText.getText().toString(), (long)123, (long)345);
            postsViewModel.addPost(newPost);

            postTitle.setText("");
            postText.setText("");

            postsViewModel.setSharedPost(newPost);
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.nav_post_detail);
        });

        return inflatedView;
    }
}