package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.function.Function;
import benji.and.mishku.inc.viaforum.DialogGenerator;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;


public class PostDetailFragment extends Fragment {

    private PostsViewModel postsViewModel;
    private TextView postTitle;
    private TextView postText;
    private Button editPostBtn;
    private Button deletePostBtn;

    //editing pop-up components refs:
    private EditText editingPostTitle;
    private EditText editingPostText;
    private Button submitPostEditBtn;
    private Button cancelPostEditingBtn;

    public PostDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postsViewModel=new ViewModelProvider(getActivity()).get(PostsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_post_detail, container, false);

        postTitle = inflatedView.findViewById(R.id.postDetailHeading);
        postTitle.setText(postsViewModel.getSharedPost().getTitle());
        postText = inflatedView.findViewById(R.id.postDetailText);
        postText.setText(postsViewModel.getSharedPost().getPostText());

        //edit post btn set-up:
        editPostBtn = inflatedView.findViewById(R.id.postDetailEditBtn);
        editPostBtn.setOnClickListener(view -> {
            DialogGenerator.showPostEditingDialog(postsViewModel.getSharedPost(), getActivity(), editPostConfirmationAction);
        });

        //delete post btn set-up:
        deletePostBtn = inflatedView.findViewById(R.id.postDetailDeleteBtn);
        deletePostBtn.setOnClickListener(view -> {
            DialogGenerator.showConfirmationDialog("title", "msg", getActivity(), deletePostConfirmationAction);
        });

        return inflatedView;
    }

    Function deletePostConfirmationAction = e->{
        postsViewModel.deletePost(postsViewModel.getSharedPost());
        Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.nav_your_posts);
        Toast.makeText(getContext(), "Post \""+postsViewModel.getSharedPost().getTitle()+"\" was DELETED", Toast.LENGTH_LONG).show();
        return null;
    };

    Function editPostConfirmationAction = e->{
        postsViewModel.updatePost(postsViewModel.getSharedPost());
        Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.nav_your_posts);
        Toast.makeText(getContext(), "Post-\""+postsViewModel.getSharedPost().getTitle()+"\" was EDITED", Toast.LENGTH_LONG).show();
        return null;
    };

}