package benji.and.mishku.inc.viaforum.views;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.function.Function;

import benji.and.mishku.inc.viaforum.ConfirmationDialogFragment;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;


public class PostDetailFragment extends Fragment {

    private PostsViewModel postsViewModel;
    private TextView postHeading;
    private TextView postText;
    private Button editPostBtn;
    private Button deletePostBtn;

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

        postHeading = inflatedView.findViewById(R.id.postDetailHeading);
        postHeading.setText(postsViewModel.getSharedPost().getTitle());
        postText = inflatedView.findViewById(R.id.postDetailText);
        postText.setText(postsViewModel.getSharedPost().getPostText());
        editPostBtn = inflatedView.findViewById(R.id.postDetailEditBtn);
        deletePostBtn = inflatedView.findViewById(R.id.postDetailDeleteBtn);
        deletePostBtn.setOnClickListener(view -> {
            ConfirmationDialogFragment.showConfirmationDialog("title", "msg", getActivity(), deletePostConfirmationAction);
        });

        return inflatedView;
    }

    Function deletePostConfirmationAction = e->{
        postsViewModel.deletePost(postsViewModel.getSharedPost());
        Navigation.findNavController(getView()).navigate(R.id.nav_your_posts);
        return null;
    };

}