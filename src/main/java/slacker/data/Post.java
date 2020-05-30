package slacker.data;

public final class Post {

    String channelId;

    String post;

    String userId;

    String messageId;

    public Post(String channelId, String post, String userId) {
        this.channelId = channelId;
        this.post = post;
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public Post() {
    }


    public static Post create(PostRequest postRequest)
    {

    }

    public static PostResponse create(Post post)
    {

    }
}
