package benji.and.mishku.inc.viaforum.repositories;
import android.app.Application;
import androidx.lifecycle.LiveData;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import benji.and.mishku.inc.viaforum.contracts.UserService;

public class UserFirebaseRepository implements UserService {


    private final UserLiveData currentUser;
    private final Application app;
    private static UserFirebaseRepository instance;

    private UserFirebaseRepository(Application app) {
        this.app = app;
        currentUser = new UserLiveData();
    }

    public static synchronized UserFirebaseRepository getInstance(Application app) {
        if(instance == null)
            instance = new UserFirebaseRepository(app);
        return instance;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public void signOut() {
        AuthUI.getInstance().signOut(app.getApplicationContext());
    }

}
