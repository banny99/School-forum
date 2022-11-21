package benji.and.mishku.inc.viaforum.views;

import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SavedPostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;
    private SubforumsViewModel subforumsViewModel;
    private SavedPostsViewModel savedPostsViewModel;
    private PostsViewModel postsViewModel;
    Icon iconSaved;
    Icon iconNotSaved;

    public PostAdapter(List<Post> posts, SubforumsViewModel subforumsViewModel, SavedPostsViewModel savedPostsViewModel) {
        this.posts = posts;
        this.subforumsViewModel = subforumsViewModel;
        this.savedPostsViewModel=savedPostsViewModel;

    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         iconSaved=Icon.createWithResource(parent.getContext(), R.drawable.ic_baseline_bookmark_added_24);
         iconNotSaved=Icon.createWithResource(parent.getContext(), R.drawable.ic_baseline_bookmark_add_24);
        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, int position) {

        viewHolder.postContent.setText(posts.get(position).getPostText());
        viewHolder.postTitle.setText(posts.get(position).getTitle());
        viewHolder.postSubforum.setText(subforumsViewModel.getSubforum(posts.get(position).getSubForumId()).getName());
        viewHolder.postAuthor.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
        boolean isPostSaved=savedPostsViewModel.isPostSavedForUser(posts.get(position).getUserId(),posts.get(position).getId());
        if(isPostSaved){
            viewHolder.saveButton.setImageIcon(iconSaved);
        }
        else{
            viewHolder.saveButton.setImageIcon(iconNotSaved);
        }
        viewHolder.saveButton.setOnClickListener((l)->{
            if(isPostSaved) {
                savedPostsViewModel.unSavePostForUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), posts.get(position).getId());
                Toast.makeText(l.getContext(), "You unsaved a post", Toast.LENGTH_SHORT).show();
                viewHolder.saveButton.setImageIcon(iconNotSaved);
            }
            else{
                savedPostsViewModel.savePostForUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),posts.get(position).getId());
                Toast.makeText(l.getContext(), "You saved a post",Toast.LENGTH_SHORT).show();
                viewHolder.saveButton.setImageIcon(iconSaved);
            }

        });
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postAuthor=itemView.findViewById(R.id.postAuthor);
            postContent = itemView.findViewById(R.id.postContent);
            postSubforum=itemView.findViewById(R.id.postSubforum);
            postTitle=itemView.findViewById(R.id.postTitle);
            saveButton=itemView.findViewById(R.id.saveButton);
            itemView.setOnClickListener(v -> {
                listener.onClick(posts.get(getAdapterPosition()));
            });
        }
    }

    public interface OnClickListener{
        void onClick(Post post);
    }
}
