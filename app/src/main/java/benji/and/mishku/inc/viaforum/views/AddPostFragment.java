package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import com.google.firebase.auth.FirebaseAuth;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;

public class AddPostFragment extends Fragment {

    private PostsViewModel postsViewModel;
    private EditText postTitle;
    private EditText postText;
    private ImageButton addPostBtn;
    private Spinner spinner;
    String[] temp;
    ArrayAdapter<Subforum> arrayAdapter;
    public AddPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        temp=new String[50];
        postsViewModel=new ViewModelProvider(requireActivity()).get(PostsViewModel.class);

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_add_post, container, false);

        //Initialize objects ...
        postTitle = inflatedView.findViewById(R.id.newPostTitle);
        postText = inflatedView.findViewById(R.id.newPostText);
        addPostBtn = inflatedView.findViewById(R.id.addNewPostButton);
        spinner=inflatedView.findViewById(R.id.spinnerSubforumChoice);

        spinner.setAdapter(arrayAdapter);
        addPostBtn.setOnClickListener(view -> {
            Subforum sub= (Subforum)spinner.getSelectedItem();
            Post newPost = new Post(postTitle.getText().toString(), postText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(), sub.getId());
            postsViewModel.addPost(newPost);

            postTitle.setText("");
            postText.setText("");

            postsViewModel.setSharedPost(newPost);
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.nav_post_detail);
        });
        return inflatedView;
    }
}