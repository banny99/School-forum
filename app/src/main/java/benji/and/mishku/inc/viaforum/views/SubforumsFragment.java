package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;

public class SubforumsFragment extends Fragment {

    private SubforumsViewModel subforumsViewModel;
    private RecyclerView subListRv;
    private SubforumAdapter subforumAdapter;
    private Button button;
    public SubforumsFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subforumAdapter=new SubforumAdapter(new ArrayList<>());
        subforumsViewModel=new ViewModelProvider(requireActivity()).get(SubforumsViewModel.class);
       subforumsViewModel.getAllSubforums().observe( this, subforums -> subforumAdapter.setSubforums(subforums));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView=inflater.inflate(R.layout.fragment_subforums, container, false);
        subListRv=inflatedView.findViewById(R.id.rvSubforums);
        subListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        subforumAdapter.setOnClickListener(subforum -> {
            subforumsViewModel.setSharedSubforum(subforum);
            Navigation.findNavController(inflatedView).navigate(R.id.nav_subforum_posts);
        });
        subListRv.setAdapter(subforumAdapter);
        return inflatedView;
    }
}