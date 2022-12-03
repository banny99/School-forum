package benji.and.mishku.inc.viaforum.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;


public class ReportedPostDetailFragment extends Fragment {

    private Post reportedPost;
    private PostsViewModel postsViewModel;
    private ImageButton acceptReportButton;
    private ImageButton denyReportButton;
    private RecyclerView reportDescriptions;
    private ReportsAdapter adapter;
    private TextView postTitle;
    private TextView postContent;
    private TextView noOfReports;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postsViewModel=new ViewModelProvider(requireActivity()).get(PostsViewModel.class);
        reportedPost=postsViewModel.getSharedPost();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView=inflater.inflate(R.layout.fragment_reported_post_detail, container, false);
        acceptReportButton=inflatedView.findViewById(R.id.acceptReportButton);
        denyReportButton=inflatedView.findViewById(R.id.denyReportButton);

        acceptReportButton.setOnClickListener(l->{
            postsViewModel.deletePost(reportedPost.getId());
            Toast.makeText(getContext(), "You deemed this post unfit for the forum.\n The post is now deleted.", Toast.LENGTH_LONG).show();
            Navigation.findNavController(inflatedView).navigate(R.id.nav_reported_posts);
        });

        denyReportButton.setOnClickListener(l->{
            Toast.makeText(getContext(), "You deemed this post fit for the forum.\n The post will stay.", Toast.LENGTH_LONG).show();
            reportedPost.setFlag(false);
            postsViewModel.updatePost(reportedPost);
            Navigation.findNavController(inflatedView).navigate(R.id.nav_reported_posts);
        });
        reportDescriptions=inflatedView.findViewById(R.id.rvReportedPostSingle);
        reportDescriptions.hasFixedSize();
        reportDescriptions.setLayoutManager(new LinearLayoutManager(getContext()));
        postTitle=inflatedView.findViewById(R.id.reportedPostTitle);
        postContent=inflatedView.findViewById(R.id.reportedPostContent);
        noOfReports=inflatedView.findViewById(R.id.noOfReports);
        if(reportedPost!=null ){
            adapter=new ReportsAdapter((ArrayList<String>) reportedPost.getFlagDescription());
            reportDescriptions.setAdapter(adapter);
            postTitle.setText(reportedPost.getTitle());
            postContent.setText(reportedPost.getPostText());
            noOfReports.setText("No of reports: "+reportedPost.getFlagCount());
        }
        return inflatedView;
    }
}


 class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder> {

    ArrayList<String> arrayList;

    public ReportsAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reported_post_description, parent, false);

        return new ReportsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReportsViewHolder holder, final int position) {
        holder.reportText.setText((position+1)+") "+arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ReportsViewHolder extends RecyclerView.ViewHolder {
        public TextView reportText;

        public ReportsViewHolder(View view) {
            super(view);

            reportText = view.findViewById(R.id.reported_item);
        }
    }
}