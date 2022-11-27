package utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Function;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;

public class SubforumAdapterAdmin extends RecyclerView.Adapter<SubforumAdapterAdmin.ViewHolder> {
    private List<Subforum> subforums;
    private SubforumsViewModel subforumsViewModel;
    public SubforumAdapterAdmin(List<Subforum> subforums,SubforumsViewModel subforumsViewModel) {
        this.subforums = subforums;
        this.subforumsViewModel=subforumsViewModel;
    }



    private SubforumAdapterAdmin.OnClickListener listener;
    public void setOnClickListener(SubforumAdapterAdmin.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubforumAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.subforum_list_item_admin, parent, false);
        return new SubforumAdapterAdmin.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubforumAdapterAdmin.ViewHolder viewHolder, int position) {


        viewHolder.subforumTitle.setText(subforums.get(position).getName());

        viewHolder.subforumDescription.setText(subforums.get(position).getDescription());

        Function deleteSubforumConfirmationAction = s -> {
            subforumsViewModel.deleteSubforum(subforums.get(position).getId());
            Toast.makeText(viewHolder.itemView.getContext(), "Post \""+subforums.get(position).getName()+"\" was DELETED", Toast.LENGTH_LONG).show();
            return null;
        };
        Function editSubforumConfirmationAction = e->{
            subforumsViewModel.updateSubforum(subforums.get(position));
            Toast.makeText(viewHolder.itemView.getContext(), "Post-\""+subforums.get(position).getName()+"\" was EDITED", Toast.LENGTH_LONG).show();
            return null;
        };
        viewHolder.editButton.setOnClickListener(l->{
            DialogGenerator.showSubforumEditingDialog(subforums.get(position), (Activity) viewHolder.itemView.getContext(), editSubforumConfirmationAction);
        });
        viewHolder.deleteButton.setOnClickListener(l->{
            DialogGenerator.showConfirmationDialog("Are you sure you want to delete this subforum?","",(Activity) viewHolder.itemView.getContext(), deleteSubforumConfirmationAction);
        });
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
        private final ImageButton editButton;
        private final ImageButton deleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subforumTitle=itemView.findViewById(R.id.subforumName);
            subforumDescription=itemView.findViewById(R.id.subforumDescription);
            editButton=itemView.findViewById(R.id.editButtonSubforum);
            deleteButton=itemView.findViewById(R.id.deleteButtonSubforum);
            itemView.setOnClickListener(v -> {
                listener.onClick(subforums.get(getAdapterPosition()));
            });
        }
    }



    public interface OnClickListener{
        void onClick(Subforum subforum);
    }
}
