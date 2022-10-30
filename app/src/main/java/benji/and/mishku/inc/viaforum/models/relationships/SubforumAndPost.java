package benji.and.mishku.inc.viaforum.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;

public class SubforumAndPost {
    @Embedded public Subforum subforum;
    @Relation(
            parentColumn = "id",
            entityColumn = "subForumId"
    )
    public List<Post> posts;
}
