package benji.and.mishku.inc.viaforum.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;

public class UserAndPost {
    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn="userId"
    )
    public List<Post> posts;
}
