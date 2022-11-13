package benji.and.mishku.inc.viaforum.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import benji.and.mishku.inc.viaforum.contracts.SubforumsService;
import benji.and.mishku.inc.viaforum.models.Subforum;

public class SubforumsViewModel extends AndroidViewModel {
    private final SubforumsService subforumsService;
    public SubforumsViewModel(@NonNull Application application, SubforumsService subforumsService) {
        super(application);
        this.subforumsService=subforumsService;
    }
    public LiveData<List<Subforum>> getAllSubforums(){
        return subforumsService.getSubforums();
    }

    public void addSubforum(Subforum subforum){
        subforumsService.addSubforum(subforum);
    }
    public void updateSubforum(Subforum subforum){subforumsService.updateSubforum(subforum);}
    public void deleteSubforum(Subforum subforum){subforumsService.deleteSubforum(subforum);}
}
