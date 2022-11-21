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
import benji.and.mishku.inc.viaforum.models.Subforum;

public class SubforumAdapter extends RecyclerView.Adapter<SubforumAdapter.ViewHolder> {
    private List<Subforum> subforums;
    public SubforumAdapter(List<Subforum> subforums) {
        this.subforums = subforums;
    }



    private SubforumAdapter.OnClickListener listener;
    public void setOnClickListener(SubforumAdapter.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubforumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.subforum_list_item, parent, false);
        return new SubforumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubforumAdapter.ViewHolder viewHolder, int position) {

        viewHolder.subforumTitle.setText(subforums.get(position).getName());

        viewHolder.subforumDescription.setText(subforums.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if(subforums ==null){
            return 0;
        }
        return subforums.size();
    }

    public void setSubforums(List<Subforum> subforums){
        this.subforums = subforums;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView subforumTitle;
        private final TextView subforumDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subforumTitle=itemView.findViewById(R.id.subforumName);
            subforumDescription=itemView.findViewById(R.id.subforumDescription);
            itemView.setOnClickListener(v -> {
                listener.onClick(subforums.get(getAdapterPosition()));
            });
        }
    }

    public interface OnClickListener{
        void onClick(Subforum subforum);
    }
}
