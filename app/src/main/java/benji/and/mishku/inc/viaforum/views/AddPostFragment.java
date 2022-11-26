package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import java.util.List;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubscriptionsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class AddPostFragment extends Fragment {

    private PostsViewModel postsViewModel;
    private SubscriptionsViewModel subscriptionsViewModel;
    private UserViewModel userViewModel;
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
        subscriptionsViewModel=new ViewModelProvider(requireActivity()).get(SubscriptionsViewModel.class);
        userViewModel=new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        final User[] currentUser = new User[1];
        userViewModel.getLoggedUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentUser[0] =user;
            }
        });

        subscriptionsViewModel.getSubforumsForUser(userViewModel.getLoggedUser().getValue()).observe(this, new Observer<List<Subforum>>() {
            @Override
            public void onChanged(@Nullable List<Subforum> subforums) {
                if(subforums!=null){
                    for (Subforum s:
                         subforums) {
                        arrayAdapter.add(s);
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
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
            Post newPost = new Post(postTitle.getText().toString(), postText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(), sub.getId(),userViewModel.getLoggedUser().getValue().getUsername());
            postsViewModel.addPost(newPost);

            postTitle.setText("");
            postText.setText("");

            postsViewModel.setSharedPost(newPost);
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.nav_post_detail);
        });
        return inflatedView;
    }
}