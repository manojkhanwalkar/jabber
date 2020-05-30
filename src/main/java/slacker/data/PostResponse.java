package slacker.data;

public final class PostResponse {

    String channelId;

    String post;

    String userId;

    String messageId;

    public PostResponse(String channelId, String post, String userId) {
        this.channelId = channelId;
        this.post = post;
        this.userId = userId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PostResponse() {
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "channelId='" + channelId + '\'' +
                ", post='" + post + '\'' +
                ", userId='" + userId + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
