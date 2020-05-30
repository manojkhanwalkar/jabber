package slacker.data;

import java.util.UUID;

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
        Post post = new Post();
        post.channelId=postRequest.getChannelId();
        post.userId = postRequest.getUserId();
        post.post = postRequest.getPost();

        post.messageId = UUID.randomUUID().toString();

        return post;
    }

    public static PostResponse create(Post post)
    {
        PostResponse response = new PostResponse();
        response.channelId=post.channelId;
        response.messageId=post.messageId;
        response.post=post.post;
        response.userId = post.userId;

        return response;

    }
}
