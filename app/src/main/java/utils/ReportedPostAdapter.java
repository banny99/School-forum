package utils;



import android.annotation.SuppressLint;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SavedPostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class ReportedPostAdapter extends RecyclerView.Adapter<ReportedPostAdapter.ViewHolder> {

    private List<Post> posts;
    private final SubforumsViewModel subforumsViewModel;
    private final PostsViewModel postsViewModel;
    private final UserViewModel userViewModel;



    public ReportedPostAdapter(List<Post> posts, SubforumsViewModel subforumsViewModel, UserViewModel userViewModel,PostsViewModel postsViewModel) {
        this.posts = posts;
        this.subforumsViewModel = subforumsViewModel;
        this.userViewModel=userViewModel;
        this.postsViewModel=postsViewModel;
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReportedPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.reported_post_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportedPostAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        if (posts != null && userViewModel.getLoggedUser().getValue() != null && !posts.isEmpty()) {
            viewHolder.postContent.setText(posts.get(position).getPostText());
            viewHolder.postTitle.setText(posts.get(position).getTitle());
            viewHolder.postSubforum.setText("posted on " + subforumsViewModel.getSubforum(posts.get(position).getSubForumId()).getName());
            viewHolder.postAuthor.setText("posted by " + posts.get(position).getPostAuthor());

            viewHolder.confirmButton.setOnClickListener(v->{
                postsViewModel.deletePost(posts.get(position).getId());
                posts.remove(position);
                Toast.makeText(viewHolder.itemView.getContext(), "You deemed this post unfit for the forum.\n The post is now deleted.", Toast.LENGTH_LONG).show();
            });
            viewHolder.denyButton.setOnClickListener(v->{
                posts.get(position).setFlag(false);
                postsViewModel.updatePost(posts.get(position));
                posts.remove(position);
                Toast.makeText(viewHolder.itemView.getContext(), "You deemed this post fit for the forum. \n The post will remain and not be deleted.", Toast.LENGTH_SHORT).show();
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
        private final ImageButton confirmButton;
        private final ImageButton denyButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postAuthor=itemView.findViewById(R.id.postAuthor);
            postContent = itemView.findViewById(R.id.postContent);
            postSubforum=itemView.findViewById(R.id.postSubforum);
            postTitle=itemView.findViewById(R.id.postTitle);
            confirmButton=itemView.findViewById(R.id.confirm_removal_button);
            denyButton=itemView.findViewById(R.id.deny_removal_button);
            itemView.setOnClickListener(v -> {
                listener.onClick(posts.get(getAdapterPosition()));
            });

        }
    }

    public interface OnClickListener{
        void onClick(Post post);
    }
}
