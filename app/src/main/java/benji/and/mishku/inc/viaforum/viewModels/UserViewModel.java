package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import benji.and.mishku.inc.viaforum.contracts.UserService;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserService userService;
    private User currentUser=null;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userService= UserRepository.getInstance(application);
    }
    public void addUser(User user){
        userService.addUser(user);
    }
    public User getUserById(Long id){

        return userService.getUserById(id);
    }



    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
