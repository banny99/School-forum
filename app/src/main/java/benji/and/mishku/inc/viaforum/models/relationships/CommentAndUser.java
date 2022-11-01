package benji.and.mishku.inc.viaforum.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.User;

public class CommentAndUser {
    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<Comment> comments;
}
