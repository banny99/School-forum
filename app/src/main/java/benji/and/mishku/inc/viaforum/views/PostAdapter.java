package benji.and.mishku.inc.viaforum.views;

import android.annotation.SuppressLint;
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
import benji.and.mishku.inc.viaforum.models.User;
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
    Icon iconSaved;
    Icon iconNotSaved;

    public PostAdapter(List<Post> posts, SubforumsViewModel subforumsViewModel, SavedPostsViewModel savedPostsViewModel, UserViewModel userViewModel) {
        this.posts = posts;
        this.subforumsViewModel = subforumsViewModel;
        this.savedPostsViewModel=savedPostsViewModel;
        this.userViewModel=userViewModel;

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
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        if (posts != null && userViewModel.getLoggedUser().getValue() != null) {
            viewHolder.postContent.setText(posts.get(position).getPostText());
            viewHolder.postTitle.setText(posts.get(position).getTitle());
            viewHolder.postSubforum.setText("posted on " + subforumsViewModel.getSubforum(posts.get(position).getSubForumId()).getName());
            viewHolder.postAuthor.setText("posted by " + posts.get(position).getPostAuthor());
            boolean isPostSaved = false;
            for (Post p :
                    userViewModel.getLoggedUser().getValue().getSavedPosts()) {
                if (p.equals(posts.get(position))) {
                    isPostSaved = true;
                    break;
                }
            }
            View.OnClickListener listenerSave = getOnListenerSave(viewHolder, position);
            View.OnClickListener listenerUnsave = getListenerUnsave(viewHolder, position, listenerSave);
            if (isPostSaved) {
                viewHolder.saveButton.setImageIcon(iconSaved);
                viewHolder.saveButton.setOnClickListener((listenerUnsave)) ;
            } else {
                viewHolder.saveButton.setImageIcon(iconNotSaved);
                viewHolder.saveButton.setOnClickListener(listenerSave);
            }


        }
    }

    @NonNull
    private View.OnClickListener getOnListenerSave(@NonNull ViewHolder viewHolder, int position) {
        View.OnClickListener listenerSave=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedPostsViewModel.savePostForUser(userViewModel.getLoggedUser().getValue(), posts.get(position));
                Toast.makeText(view.getContext(), "You saved a post", Toast.LENGTH_SHORT).show();
                viewHolder.saveButton.setImageIcon(iconSaved);
                view.setOnClickListener(getListenerUnsave(viewHolder,position,this));
            }
        };
        return listenerSave;
    }

    @NonNull
    private View.OnClickListener getListenerUnsave(@NonNull ViewHolder viewHolder, int position, View.OnClickListener listenerSave) {
        View.OnClickListener listenerUnsave=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedPostsViewModel.unSavePostForUser(userViewModel.getLoggedUser().getValue(), posts.get(position));
                Toast.makeText(view.getContext(), "You unsaved a post", Toast.LENGTH_SHORT).show();
                viewHolder.saveButton.setImageIcon(iconNotSaved);
                view.setOnClickListener(listenerSave);
            }
        };
        return listenerUnsave;
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
