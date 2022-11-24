package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.UserFirebaseRepository;

public interface UserService {
    void addUser(User user);
    void deleteUser(String userId);
    void updateUser(User user);

    void getUserById(String uid, UserFirebaseRepository.UserCallBack c);

    LiveData<User> getPostAuthor(Post post);
    LiveData<List<User>> getAllUsers();
}
