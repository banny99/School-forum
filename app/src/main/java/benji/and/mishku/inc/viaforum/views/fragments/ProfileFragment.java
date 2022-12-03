package benji.and.mishku.inc.viaforum.views.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.function.Function;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;
import utils.DialogGenerator;

public class ProfileFragment extends Fragment {

    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageButton signOutBtn = inflatedView.findViewById(R.id.logout_btn);
        signOutBtn.setOnClickListener(view -> {
            userViewModel.logOut();
            FirebaseAuth.getInstance().signOut();
        });

        EditText username = inflatedView.findViewById(R.id.profile_username);
        userViewModel.getLoggedUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                username.setText(user.getUsername());
            }
        });
        Button changeUsernameBtn = inflatedView.findViewById(R.id.profile_change_username_btn);
        changeUsernameBtn.setOnClickListener(v -> {
            DialogGenerator.showChangeUsernameDialog(Objects.requireNonNull(userViewModel.getLoggedUser().getValue()), getActivity(), editUsernameConfirmationAction);
        });

        Button changePasswordBtn = inflatedView.findViewById(R.id.profile_change_password_btn);
        changePasswordBtn.setOnClickListener(v -> {
            DialogGenerator.showChangePasswordDialog(FirebaseAuth.getInstance().getCurrentUser(), getActivity(), editPasswordConfirmationAction);
        });

        return inflatedView;
    }


    Function editUsernameConfirmationAction = e->{
        userViewModel.updateUser();
        Toast.makeText(getContext(), "Your username-\""+userViewModel.getLoggedUser().getValue().getUsername()+"\" was EDITED", Toast.LENGTH_LONG).show();
        return null;
    };
    Function editPasswordConfirmationAction = e->{
        Toast.makeText(getContext(), "Your password was EDITED", Toast.LENGTH_LONG).show();
        return null;
    };
}