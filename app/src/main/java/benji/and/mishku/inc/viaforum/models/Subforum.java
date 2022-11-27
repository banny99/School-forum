package benji.and.mishku.inc.viaforum.models;

import java.util.List;
import java.util.Objects;

public class Subforum {
    private String id;
    private String name;
    private String description;
    public Subforum() {
        //required by firebase
    }

    public Subforum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subforum)) return false;
        Subforum subforum = (Subforum) o;
        return id.equals(subforum.id) && name.equals(subforum.name) && description.equals(subforum.description) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
