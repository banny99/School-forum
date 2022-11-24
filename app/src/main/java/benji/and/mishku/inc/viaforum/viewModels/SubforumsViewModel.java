package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.Objects;

import benji.and.mishku.inc.viaforum.contracts.SubforumsService;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.SubForumFirebaseRepository;

public class SubforumsViewModel extends AndroidViewModel {
    private final SubforumsService subforumsService;
    private Subforum sharedSubforum;
    private MutableLiveData<Subforum> subforumById;
    public SubforumsViewModel(@NonNull Application application) {
        super(application);
        this.subforumsService= SubForumFirebaseRepository.getInstance();
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
        List<Subforum> subforums=subforumsService.getSubforums().getValue();
        for (Subforum s:
             subforums) {
            if(Objects.equals(s.getId(), subforumId)){
                return s;
            }
        }
        return null;
    }
    public void addSubforum(Subforum subforum){
        subforumsService.addSubforum(subforum);
    }
    public void updateSubforum(Subforum subforum){subforumsService.updateSubforum(subforum);}
    public void deleteSubforum(String subforumId){subforumsService.deleteSubforum(subforumId);}
}
