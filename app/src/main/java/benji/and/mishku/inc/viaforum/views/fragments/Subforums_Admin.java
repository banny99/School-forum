package benji.and.mishku.inc.viaforum.views.fragments;

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
import utils.SubforumAdapter;
import utils.SubforumAdapterAdmin;


public class Subforums_Admin extends Fragment {

    private SubforumsViewModel subforumsViewModel;
    private RecyclerView subListRv;
    private SubforumAdapterAdmin subforumAdapter;
    private Button button;
    public Subforums_Admin(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subforumsViewModel=new ViewModelProvider(requireActivity()).get(SubforumsViewModel.class);
        subforumAdapter=new SubforumAdapterAdmin(new ArrayList<>(),subforumsViewModel);
        subforumsViewModel.getAllSubforums().observe( this, subforums -> subforumAdapter.setSubforums(subforums));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView=inflater.inflate(R.layout.fragment_subforums__admin, container, false);
        subListRv=inflatedView.findViewById(R.id.rvSubforumsAdmin);
        subListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        subforumAdapter.setOnClickListener(subforum -> {
            subforumsViewModel.setSharedSubforum(subforum);
            Navigation.findNavController(inflatedView).navigate(R.id.nav_subforum_posts);
        });
        subListRv.setAdapter(subforumAdapter);
        return inflatedView;
    }
}