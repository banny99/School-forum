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

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PostsViewModel postsViewModel;
    private EditText postTitle;
    private EditText postText;
    private Button addPostBtn;

    public AddPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        postsViewModel=new ViewModelProvider(getActivity()).get(PostsViewModel.class);
        //postTitle = getActivity().findViewById(R.id.postTitle);
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
            Post newPost = new Post(postTitle.getText().toString(), postText.getText().toString(), (long)123, (long)345, Instant.now());
            postsViewModel.addPost(newPost);

            postTitle.setText("");
            postText.setText("");

            postsViewModel.setSharedPost(newPost);
            Navigation.findNavController(inflatedView).navigate(R.id.nav_post_detail);
        });

        return inflatedView;
    }
}