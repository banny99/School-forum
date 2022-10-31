package benji.and.mishku.inc.viaforum.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;
    public PostAdapter(List<Post> posts) {
        this.posts = posts;
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
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, int position) {
        viewHolder.postTitle.setText(posts.get(position).getTitle());
        viewHolder.postContent.setText(posts.get(position).getPostText());
    }

    @Override
    public int getItemCount() {
        if(posts==null){
            return 0;
        }
        return posts.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView postTitle;
        private final TextView postContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postAuthor);

            itemView.setOnClickListener(v -> {
                listener.onClick(posts.get(getAdapterPosition()));
            });
        }
    }

    public interface OnClickListener{
        void onClick(Post post);
    }
}
