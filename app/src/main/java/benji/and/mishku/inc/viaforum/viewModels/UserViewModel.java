package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import benji.and.mishku.inc.viaforum.contracts.UserService;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.UserFirebaseRepository;
import benji.and.mishku.inc.viaforum.repositories.UserLiveData;

public class UserViewModel extends AndroidViewModel {
    private final UserService userService;
    private FirebaseAuth firebaseAuth;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userService= UserFirebaseRepository.getInstance(application);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userService.getCurrentUser();
    }

//    public void addUser(User user){
//        userService.addUser(user);
//    }
//    public User getUserByUsername(String username){
//        return userService.getUserByUsername(username);
//    }
}
