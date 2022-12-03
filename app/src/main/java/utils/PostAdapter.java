package utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.viewModels.CommentsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SavedPostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;
    private SubforumsViewModel subforumsViewModel;
    private SavedPostsViewModel savedPostsViewModel;
    private PostsViewModel postsViewModel;
    private UserViewModel userViewModel;
    private CommentsViewModel commentsViewModel;



    public PostAdapter(List<Post> posts, SubforumsViewModel subforumsViewModel, SavedPostsViewModel savedPostsViewModel, UserViewModel userViewModel, CommentsViewModel commentsViewModel) {
        this.posts = posts;
        this.subforumsViewModel = subforumsViewModel;
        this.savedPostsViewModel=savedPostsViewModel;
        this.userViewModel=userViewModel;
        this.commentsViewModel=commentsViewModel;
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.post_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        if (posts != null && userViewModel.getLoggedUser().getValue() != null && !posts.isEmpty()) {
            viewHolder.postContent.setText(posts.get(position).getPostText());
            viewHolder.postTitle.setText(posts.get(position).getTitle());
            if (subforumsViewModel.getSubforum(posts.get(position).getSubForumId()) != null)
                viewHolder.postSubforum.setText("posted on " + subforumsViewModel.getSubforum(posts.get(position).getSubForumId()).getName());
            else
                viewHolder.postSubforum.setVisibility(View.GONE);
            viewHolder.postAuthor.setText("posted by " + posts.get(position).getPostAuthor());

            boolean isPostSaved=userViewModel.isPostSavedForCurrentUser(posts.get(position));
            if (isPostSaved) {
                viewHolder.saveButton.setVisibility(View.GONE);
                viewHolder.unSaveButton.setVisibility(View.VISIBLE);
            } else {
                viewHolder.unSaveButton.setVisibility(View.GONE);
                viewHolder.saveButton.setVisibility(View.VISIBLE);
            }
            viewHolder.saveButton.setOnClickListener(v -> {
                savedPostsViewModel.savePostForUser(userViewModel.getLoggedUser().getValue(), posts.get(position));
                viewHolder.saveButton.setVisibility(View.GONE);
                viewHolder.unSaveButton.setVisibility(View.VISIBLE);
                Toast.makeText(viewHolder.itemView.getContext(), "You saved a post\nYou can now see it in Saved Posts", Toast.LENGTH_LONG).show();


            }) ;
            viewHolder.unSaveButton.setOnClickListener(v -> {
                savedPostsViewModel.unSavePostForUser(userViewModel.getLoggedUser().getValue(), posts.get(position));
                viewHolder.unSaveButton.setVisibility(View.GONE);
                viewHolder.saveButton.setVisibility(View.VISIBLE);
                Toast.makeText(viewHolder.itemView.getContext(), "You unSaved a post\nIt was removed from your Saved Posts", Toast.LENGTH_LONG).show();

            });


        }
    }





    @Override
    public int getItemCount() {
        if(posts==null){
            return 0;
        }
        return posts.size();
    }

    public void setPosts(List<Post> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView postContent;
        private final TextView postAuthor;
        private final TextView postSubforum;
        private final TextView postTitle;
        private final ImageButton saveButton;
        private final ImageButton unSaveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postAuthor=itemView.findViewById(R.id.postAuthor);
            postContent = itemView.findViewById(R.id.postContent);
            postSubforum=itemView.findViewById(R.id.postSubforum);
            postTitle=itemView.findViewById(R.id.postTitle);
            saveButton=itemView.findViewById(R.id.saveButton);
            unSaveButton=itemView.findViewById(R.id.unSaveButton);

            itemView.setOnClickListener(v -> {
                listener.onClick(posts.get(getAdapterPosition()));
            });

        }
    }

    public interface OnClickListener{
        void onClick(Post post);
    }
}
