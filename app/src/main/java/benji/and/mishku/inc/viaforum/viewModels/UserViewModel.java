package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import benji.and.mishku.inc.viaforum.contracts.UserService;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.UserFirebaseRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserService userService;
    private MutableLiveData<User> loggedUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userService = UserFirebaseRepository.getInstance();
        loggedUser = new MutableLiveData<>();
    }

    public LiveData<User> getLoggedUser(){
        if (loggedUser.getValue() == null)
            userService.getUserById(FirebaseAuth.getInstance().getCurrentUser().getUid(), user -> {
                loggedUser.setValue(user);
            });
        return loggedUser;
    }

    public void addUser(User user){
        userService.addUser(user);
    }
    public void updateUser(User user){
        userService.updateUser(user);
    }
    public void deleteUser(User user){
        loggedUser = null;
        userService.deleteUser(user.getUserId());
    }

    public void logOut(){
        loggedUser = null;
    }
}