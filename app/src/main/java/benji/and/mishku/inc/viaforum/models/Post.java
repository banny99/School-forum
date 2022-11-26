package benji.and.mishku.inc.viaforum.models;

import java.util.Objects;

public class Post {
    private String id;
    private String title;
    private String postText;
    private String userId;
    private String subForumId;
    private boolean flag;
    private String flagDescription;
    private DateTime dateTime;
    private String postAuthor;
    public Post() {
        //required by Firebase
    }

    public Post(String title, String postText, String userId, String subForumId, String postAuthor) {
        this.title = title;
        this.postText = postText;
        this.userId = userId;
        this.subForumId = subForumId;
        this.postAuthor=postAuthor;
        dateTime = DateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubForumId() {
        return subForumId;
    }

    public void setSubForumId(String subForumId) {
        this.subForumId = subForumId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getFlagDescription() {
        return flagDescription;
    }

    public void setFlagDescription(String flagDescription) {
        this.flagDescription = flagDescription;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, postText, userId, subForumId, flag, flagDescription, dateTime, postAuthor);
    }
}

