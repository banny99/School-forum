package benji.and.mishku.inc.viaforum.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import benji.and.mishku.inc.viaforum.DialogGenerator;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.viewModels.CommentsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;


public class PostDetailFragment extends Fragment {

    private PostsViewModel postsViewModel;
    private CommentsViewModel commentsViewModel;
    private TextView postTitle;
    private TextView postText;
    private TextView postSubforum;
    private TextView postAuthor;
    private ImageButton editPostBtn;
    private ImageButton deletePostBtn;
    private ImageButton addComment;
    private EditText commentText;
    //editing pop-up components refs:
    private EditText editingPostTitle;
    private EditText editingPostText;
    private ImageButton submitPostEditBtn;
    private ImageButton cancelPostEditingBtn;
    private ImageButton reportButton;
    private CommentAdapter commentAdapter;
    private RecyclerView commentListRV;

    public PostDetailFragment() {
        // Required empty public constructor
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commentsViewModel=new ViewModelProvider(requireActivity()).get(CommentsViewModel.class);
        commentAdapter=new CommentAdapter(new ArrayList<>());
        postsViewModel=new ViewModelProvider(requireActivity()).get(PostsViewModel.class);
        commentsViewModel.getCommentsForPost(postsViewModel.getSharedPost()).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                commentAdapter.setComments(comments);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_post_detail, container, false);
        commentListRV=inflatedView.findViewById(R.id.rvComment);
        commentListRV.hasFixedSize();
        commentListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        postTitle = inflatedView.findViewById(R.id.singlePostTitle);
        postTitle.setText(postsViewModel.getSharedPost().getTitle());
        postText = inflatedView.findViewById(R.id.singlePostContent);
        postText.setText(postsViewModel.getSharedPost().getPostText());
        postSubforum=inflatedView.findViewById(R.id.singlePostSubforum);
        postSubforum.setText(getString(R.string.posted_on) + Math.toIntExact(postsViewModel.getSharedPost().getSubForumId()));
        postAuthor=inflatedView.findViewById(R.id.singlePostAuthor);
        postAuthor.setText(getString(R.string.posted_by)+Math.toIntExact(postsViewModel.getSharedPost().getUserId()));
        //edit post btn set-up:
        editPostBtn = inflatedView.findViewById(R.id.editPostButton);
        editPostBtn.setOnClickListener(view -> {
            DialogGenerator.showPostEditingDialog(postsViewModel.getSharedPost(), getActivity(), editPostConfirmationAction);
        });
        commentText=inflatedView.findViewById(R.id.addCommentText);
        addComment=inflatedView.findViewById(R.id.addCommentButton);

            addComment.setOnClickListener((view -> {
                if(commentText.getText()!=null && !commentText.getText().toString().equals("")){
                commentsViewModel.addComment(new Comment(commentText.getText().toString(),1L,postsViewModel.getSharedPost().getId()));

                Toast.makeText(getContext(), "Your comment has been added", Toast.LENGTH_SHORT).show();
                commentText.setText("");
                }
                else Toast.makeText(getContext(), "Your comment is empty, cannot add", Toast.LENGTH_SHORT).show();

            }));

        //delete post btn set-up:
        deletePostBtn = inflatedView.findViewById(R.id.deletePostButton);
        deletePostBtn.setOnClickListener(view -> {
            DialogGenerator.showConfirmationDialog("title", "msg", getActivity(), deletePostConfirmationAction);
        });

        reportButton=inflatedView.findViewById(R.id.reportButton);
        reportButton.setOnClickListener(l->{
            DialogGenerator.showPostReportingDialog(postsViewModel.getSharedPost(), getActivity(), reportPostConfirmationAction);
        });
        commentListRV.setAdapter(commentAdapter);
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

    Function reportPostConfirmationAction = e->{
        postsViewModel.updatePost(postsViewModel.getSharedPost());
        Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.nav_your_posts);
        Toast.makeText(getContext(), "Post-\""+postsViewModel.getSharedPost().getTitle()+"\" was reported, we will review your complaint and let you know.", Toast.LENGTH_LONG).show();
        return null;
    };

}