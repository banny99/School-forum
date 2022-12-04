package utils;

import android.app.Activity;
import android.app.Dialog;
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
import java.util.Objects;
import java.util.function.Function;

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
        viewHolder.commentAuthor.setText("@"+comments.get(position).getCommentAuthor());
        //if the comment is written by logged user ->make comments options visible
        if (comments.get(position).getUserId().equals(Objects.requireNonNull(userViewModel.getLoggedUser().getValue()).getUserId())){
            viewHolder.editCommentBtn.setVisibility(View.VISIBLE);
            viewHolder.deleteCommentBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.editCommentBtn.setVisibility(View.GONE);
            viewHolder.deleteCommentBtn.setVisibility(View.GONE);
        }
        if(postsViewModel.getPostById(comments.get(position).getPostId()).getUserId().equals(Objects.requireNonNull(userViewModel.getLoggedUser().getValue().getUserId()))) {
            if (comments.get(position).isRightAnswer()) {
                viewHolder.itemView.setBackgroundResource(R.color.rightanswerbackground);
                viewHolder.markCorrectAnswerBtn.setVisibility(View.GONE);
                viewHolder.unMarkAnswerBtn.setVisibility(View.VISIBLE);
            } else {
                viewHolder.itemView.setBackgroundResource(R.color.card_background_color);
                viewHolder.unMarkAnswerBtn.setVisibility(View.GONE);
                viewHolder.markCorrectAnswerBtn.setVisibility(View.VISIBLE);
            }
            viewHolder.markCorrectAnswerBtn.setOnClickListener(view -> {
                Toast.makeText(viewHolder.itemView.getContext(), " This comment was marked as correct.", Toast.LENGTH_LONG).show();
                Toast.makeText(viewHolder.itemView.getContext(), " If you want to undo, click the button again.", Toast.LENGTH_LONG).show();
                Comment c = comments.get(position);
                c.setRightAnswer(true);
                commentsViewModel.updateComment(c);
                viewHolder.unMarkAnswerBtn.setVisibility(View.GONE);
            });
            viewHolder.unMarkAnswerBtn.setOnClickListener(view -> {
                Toast.makeText(viewHolder.itemView.getContext(), " This comment was unmarked as the right answer.", Toast.LENGTH_LONG).show();
                Comment c = comments.get(position);
                c.setRightAnswer(false);
                commentsViewModel.updateComment(c);
                viewHolder.markCorrectAnswerBtn.setVisibility(View.VISIBLE);
            });
        }
        else{
            viewHolder.markCorrectAnswerBtn.setVisibility(View.GONE);
            viewHolder.unMarkAnswerBtn.setVisibility(View.GONE);
        }

        Function editCommentConfirmationAction = e->{
            commentsViewModel.updateComment(comments.get(position));
            Toast.makeText(viewHolder.itemView.getContext(), "Your comment was EDITED", Toast.LENGTH_LONG).show();
            return null;
        };
        Function deleteCommentConfirmationAction = s -> {
            commentsViewModel.deleteComment(comments.get(position).getId());
            Toast.makeText(viewHolder.itemView.getContext(), "Your comment was DELETED", Toast.LENGTH_LONG).show();
            return null;
        };
        viewHolder.editCommentBtn.setOnClickListener(l->{
            DialogGenerator.showCommentEditingDialog(comments.get(position),(Activity) viewHolder.itemView.getContext(), editCommentConfirmationAction);
        });
        viewHolder.deleteCommentBtn.setOnClickListener(view -> {
            DialogGenerator.showConfirmationDialog("Are you sure you want to delete this comment?","",(Activity) viewHolder.itemView.getContext(), deleteCommentConfirmationAction);
        });

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
        private final ImageButton editCommentBtn;
        private final ImageButton deleteCommentBtn;
        private final ImageButton markCorrectAnswerBtn;
        private final ImageButton unMarkAnswerBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAuthor=itemView.findViewById(R.id.commentAuthor);
            commentContent=itemView.findViewById(R.id.commentContent);

            commentOptionsBar=itemView.findViewById(R.id.comment_options_bar);
            editCommentBtn=itemView.findViewById(R.id.editCommentButton);
            deleteCommentBtn=itemView.findViewById(R.id.deleteCommentButton);

            markCorrectAnswerBtn=itemView.findViewById(R.id.mark_correct_answer_btn);
            unMarkAnswerBtn=itemView.findViewById(R.id.unMarkCommentButton);

        }
    }



}
