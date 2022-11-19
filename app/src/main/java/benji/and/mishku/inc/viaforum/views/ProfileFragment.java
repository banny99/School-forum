package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;


public class ProfileFragment extends Fragment {

    private UserViewModel userViewModel;
    private TextView text;
    private Button signOutBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel=new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        text = inflatedView.findViewById(R.id.profile_heading);
        text.setText("Hello " + userViewModel.getCurrentUser().getValue().getEmail());

        signOutBtn = inflatedView.findViewById(R.id.signOut_btn);
        signOutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
        });

        return inflatedView;
    }
}