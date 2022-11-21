package benji.and.mishku.inc.viaforum.contracts;

import benji.and.mishku.inc.viaforum.models.User;

public interface UserService {
    User logUserIn(String username, String password);
    User getUserById(Long userId);
    void addUser(User user);
}
