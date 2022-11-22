package benji.and.mishku.inc.viaforum.models;

import java.util.List;

public class Subforum {
    private String id;
    private String name;
    private String description;
    private List<User> users;
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
}
