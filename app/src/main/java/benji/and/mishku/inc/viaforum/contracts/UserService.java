package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

import benji.and.mishku.inc.viaforum.models.User;

public interface UserService {
//    User logUserIn(String username, String password);
//    User getUserByUsername(String username);
//    void addUser(User user);

    LiveData<FirebaseUser> getCurrentUser();
}
