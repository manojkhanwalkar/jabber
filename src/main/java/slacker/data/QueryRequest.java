package slacker.data;

public class QueryRequest {

    String channelId;
    String lastMessageId;

    public QueryRequest(String channelId, String lastMessageId) {
        this.channelId = channelId;
        this.lastMessageId = lastMessageId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(String lastMessageId) {
        this.lastMessageId = lastMessageId;
    }


    public QueryRequest() {
    }
}

