package benji.and.mishku.inc.viaforum.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;

public class CommentAndPost {
    @Embedded public Post post;
    @Relation(
            parentColumn = "id",
            entityColumn = "postId"
    )
    public List<Comment> comments;
}
