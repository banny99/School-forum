package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseUser;

import benji.and.mishku.inc.viaforum.contracts.UserService;
import benji.and.mishku.inc.viaforum.repositories.UserFirebaseRepository;

public class SignInViewModel extends AndroidViewModel {
    private final UserService userService;

    public SignInViewModel(Application app){
        super(app);
        userService = UserFirebaseRepository.getInstance(app);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userService.getCurrentUser();
    }
}