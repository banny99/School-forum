package utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubscriptionsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class SubforumAdapter extends RecyclerView.Adapter<SubforumAdapter.ViewHolder> {
    private List<Subforum> subforums;
    private UserViewModel userViewModel;
    private SubscriptionsViewModel subscriptionsViewModel;
    public SubforumAdapter(List<Subforum> subforums, UserViewModel userViewModel, SubscriptionsViewModel subscriptionsViewModel) {
        this.subforums = subforums;
        this.userViewModel=userViewModel;
        this.subscriptionsViewModel=subscriptionsViewModel;
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
        boolean isSubscribedToSubforum= userViewModel.isCurrentUserSubscribedToSubforum(subforums.get(position));
        if (isSubscribedToSubforum) {
            viewHolder.subscribeButton.setVisibility(View.GONE);
            viewHolder.unSubscribeButton.setVisibility(View.VISIBLE);
        } else {
            viewHolder.unSubscribeButton.setVisibility(View.GONE);
            viewHolder.subscribeButton.setVisibility(View.VISIBLE);
        }
        viewHolder.subscribeButton.setOnClickListener(v -> {
            subscriptionsViewModel.subscribeToSubforum(userViewModel.getLoggedUser().getValue(), subforums.get(position));
            viewHolder.subscribeButton.setVisibility(View.GONE);
            viewHolder.unSubscribeButton.setVisibility(View.VISIBLE);
            Toast.makeText(viewHolder.itemView.getContext(), "You subscribed to this subforum.\nYou can now see post on it and see all related posts in your home page.", Toast.LENGTH_LONG).show();


        }) ;
        viewHolder.unSubscribeButton.setOnClickListener(v -> {
            subscriptionsViewModel.unSubscribeFromSubforum(userViewModel.getLoggedUser().getValue(), subforums.get(position));
            viewHolder.unSubscribeButton.setVisibility(View.GONE);
            viewHolder.subscribeButton.setVisibility(View.VISIBLE);
            Toast.makeText(viewHolder.itemView.getContext(), "You unsubscribed from a subforum.\nNow you cannot post on it anymore or see content related to it.", Toast.LENGTH_LONG).show();

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
        private final Button subscribeButton;
        private final Button unSubscribeButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subforumTitle=itemView.findViewById(R.id.subforumName);
            subforumDescription=itemView.findViewById(R.id.subforumDescription);
            subscribeButton=itemView.findViewById(R.id.subscribeButtonOnList);
            unSubscribeButton=itemView.findViewById(R.id.unsubscribeButtonOnList);
            itemView.setOnClickListener(v -> {
                listener.onClick(subforums.get(getAdapterPosition()));
            });
        }
    }

    public interface OnClickListener{
        void onClick(Subforum subforum);
    }
}
