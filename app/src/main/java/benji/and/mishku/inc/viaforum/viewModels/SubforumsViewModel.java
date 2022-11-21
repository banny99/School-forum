package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.contracts.SubforumsService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.repositories.SubForumFirebaseRepository;
import benji.and.mishku.inc.viaforum.repositories.SubforumsRepository;

public class SubforumsViewModel extends AndroidViewModel {
    private final SubforumsService subforumsService;
    private Subforum sharedSubforum;
    public SubforumsViewModel(@NonNull Application application) {
        super(application);
        this.subforumsService= SubForumFirebaseRepository.getInstance(application);
    }
    public LiveData<List<Subforum>> getAllSubforums(){
        return subforumsService.getSubforums();
    }

    public Subforum getSharedSubforum() {
        return sharedSubforum;
    }

    public void setSharedSubforum(Subforum sharedSubforum) {
        this.sharedSubforum = sharedSubforum;
    }
    public Subforum getSubforum(String subforumId){
        return subforumsService.getSubforumById(subforumId);
    }
    public void addSubforum(Subforum subforum){
        subforumsService.addSubforum(subforum);
    }
    public void updateSubforum(Subforum subforum){subforumsService.updateSubforum(subforum);}
    public void deleteSubforum(Subforum subforum){subforumsService.deleteSubforum(subforum);}
}
