package benji.and.mishku.inc.viaforum.contracts;

import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.repositories.SubForumFirebaseRepository;

public interface SubforumsService {
    LiveData<List<Subforum>> getSubforums();
    void addSubforum(Subforum s);
    void updateSubforum(Subforum s);
    void deleteSubforum(String subforumId);
    LiveData<Subforum> getSubforumById(String subForumId);

}
