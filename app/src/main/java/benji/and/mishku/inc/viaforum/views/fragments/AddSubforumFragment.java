package benji.and.mishku.inc.viaforum.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;


public class AddSubforumFragment extends Fragment {

    private SubforumsViewModel subforumsViewModel;

    private EditText subforumTitle;
    private EditText subforumDescription;
    private ImageButton addSubforumBtn;

    public AddSubforumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subforumsViewModel=new ViewModelProvider(requireActivity()).get(SubforumsViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_add_subforum, container, false);

        //Initialize objects ...
        subforumTitle = inflatedView.findViewById(R.id.newSubforumTitle);
        subforumDescription = inflatedView.findViewById(R.id.newSubforumDescription);
        addSubforumBtn = inflatedView.findViewById(R.id.addNewSubforumButton);


        addSubforumBtn.setOnClickListener(view -> {
            if(subforumTitle!=null && !subforumTitle.getText().toString().equals("") && subforumDescription!=null && !subforumDescription.getText().toString().equals("")){
                Subforum newSubforum = new Subforum(subforumTitle.getText().toString(),subforumDescription.getText().toString());
                subforumsViewModel.addSubforum(newSubforum);
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_admin).navigateUp();
            }
            else{
                Toast.makeText(getContext(), "A subforum needs both a title and a description to be added.", Toast.LENGTH_SHORT).show();
            }

        });
        return inflatedView;
    }
}