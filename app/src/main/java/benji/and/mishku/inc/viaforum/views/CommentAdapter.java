package benji.and.mishku.inc.viaforum.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.viewModels.CommentsViewModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> comments;
    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }



    private CommentAdapter.OnClickListener listener;
    public void setOnClickListener(CommentAdapter.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_list_item, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder viewHolder, int position) {

        viewHolder.commentContent.setText(comments.get(position).getContent());

        viewHolder.commentAuthor.setText("some user");
    }

    @Override
    public int getItemCount() {
        if(comments ==null){
            return 0;
        }
        return comments.size();
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView commentContent;
        private final TextView commentAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAuthor=itemView.findViewById(R.id.commentAuthor);
            commentContent=itemView.findViewById(R.id.commentContent);
            itemView.setOnClickListener(v -> {
                listener.onClick(comments.get(getAdapterPosition()));
            });
        }
    }

    public interface OnClickListener{
        void onClick(Comment comment);
    }

}
