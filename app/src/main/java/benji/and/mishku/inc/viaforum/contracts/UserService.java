package benji.and.mishku.inc.viaforum.contracts;

import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.UserFirebaseRepository;

public interface UserService {
    public void addUser(User user);
    public void deleteUser(String userId);
    public void updateUser(User user);

    public void getUserById(String uid, UserFirebaseRepository.UserCallBack c);
}
