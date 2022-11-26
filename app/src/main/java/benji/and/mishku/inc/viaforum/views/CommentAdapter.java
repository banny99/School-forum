package benji.and.mishku.inc.viaforum.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.viewModels.CommentsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> comments;
    private UserViewModel userViewModel;
    private CommentsViewModel commentsViewModel;
    private PostsViewModel postsViewModel;
    public CommentAdapter(List<Comment> comments, UserViewModel userViewModel, CommentsViewModel commentsViewModel, PostsViewModel postsViewModel) {
        this.comments = comments;
        this.userViewModel = userViewModel;
        this.commentsViewModel = commentsViewModel;
        this.postsViewModel = postsViewModel;
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
        //ToDo: put author username
        //viewHolder.commentAuthor.setText(userViewModel.getCommentAuthor(comments.get(position).getUserId()));
        viewHolder.commentAuthor.setText("some author");
        //make background of the right answer/comment green
        if (comments.get(position).isRightAnswer())
            viewHolder.itemView.setBackgroundResource(R.color.rightanswerbackground);
        //if the comment is written by logged user ->make comments options visible
        if (comments.get(position).getUserId().equals(userViewModel.getLoggedUser().getValue().getUserId()))
            viewHolder.commentOptionsBar.setVisibility(View.VISIBLE);
        else
            viewHolder.commentOptionsBar.setVisibility(View.GONE);
        //if the post is written by logged user ->make 'mark-correct' button visible
        if (postsViewModel.getSharedPost().getUserId().equals(userViewModel.getLoggedUser().getValue().getUserId()))
            viewHolder.markCorrectAnswerBtn.setVisibility(View.VISIBLE);
        else
            viewHolder.markCorrectAnswerBtn.setVisibility(View.GONE);
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

        private LinearLayout commentOptionsBar;
        private ImageButton editCommentBtn;
        private ImageButton deleteCommentBtn;
        private ImageButton markCorrectAnswerBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAuthor=itemView.findViewById(R.id.commentAuthor);
            commentContent=itemView.findViewById(R.id.commentContent);

            commentOptionsBar=itemView.findViewById(R.id.comment_options_bar);
            editCommentBtn=itemView.findViewById(R.id.editCommentButton);
            editCommentBtn.setOnClickListener(view -> {
                //ToDo: on edit-comment Btn pressed.
            });
            deleteCommentBtn=itemView.findViewById(R.id.deleteCommentButton);
            deleteCommentBtn.setOnClickListener(view -> {
                commentsViewModel.deleteComment(comments.get(getAdapterPosition()).getId());
            });

            markCorrectAnswerBtn=itemView.findViewById(R.id.mark_correct_answer_btn);
            markCorrectAnswerBtn.setOnClickListener(view -> {
                Toast.makeText(itemView.getContext(), comments.get(getAdapterPosition()).getId()+" -marked as correct", Toast.LENGTH_LONG).show();
                Comment c = comments.get(getAdapterPosition());
                c.setRightAnswer(true);
                commentsViewModel.updateComment(c);
            });
            itemView.setOnClickListener(v -> {
                listener.onClick(comments.get(getAdapterPosition()));
            });
        }
    }

    public interface OnClickListener{
        void onClick(Comment comment);
    }

}
