package benji.and.mishku.inc.viaforum.repositories;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Converters;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.repositories.DAO.CommentsDAO;
import benji.and.mishku.inc.viaforum.repositories.DAO.PostsDAO;
import benji.and.mishku.inc.viaforum.repositories.DAO.SubforumsDAO;
import benji.and.mishku.inc.viaforum.repositories.DAO.UserDAO;

@Database(entities = {User.class, Post.class, Subforum.class, Comment.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class ForumDatabase extends RoomDatabase {
    private static  ForumDatabase instance;
    public abstract UserDAO userDAO();
    public abstract PostsDAO postsDAO();
    public abstract SubforumsDAO subforumsDAO();
    public abstract CommentsDAO commentsDAO();
    public static synchronized   ForumDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), ForumDatabase.class,"forum_database")
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
}
