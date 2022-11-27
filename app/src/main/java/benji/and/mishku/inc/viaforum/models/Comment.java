package benji.and.mishku.inc.viaforum.models;

public class Comment {
    private String id;
    private String content;
    private String userId;
    private String postId;
    private boolean isRightAnswer;
    private String commentAuthor="";
    public Comment() {
    }

    public Comment(String content, String userId, String postId) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        isRightAnswer = false;
    }
    public Comment(String content, String userId, String postId, String commentAuthor) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.commentAuthor=commentAuthor;
        isRightAnswer = false;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }
}
