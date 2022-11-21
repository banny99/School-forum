package benji.and.mishku.inc.viaforum.models;


public class Post {
    private String id;
    private String title;
    private String postText;
    private String userId;
    private Long subForumId;
    private boolean flag;
    private String flagDescription;
    private DateTime dateTime;

    public Post() {
        //required by Firebase
    }

    public Post(String title, String postText, String userId, Long subForumId) {
        this.title = title;
        this.postText = postText;
        this.userId = userId;
        this.subForumId = subForumId;
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

    public Long getSubForumId() {
        return subForumId;
    }

    public void setSubForumId(Long subForumId) {
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
}

