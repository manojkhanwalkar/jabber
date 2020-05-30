package slacker.data;

import java.util.ArrayList;

public class QueryResponse {


    ArrayList<PostResponse> postResponses = new ArrayList<>();

    String lastMessageId;


    public ArrayList<PostResponse> getPostResponses() {
        return postResponses;
    }

    public void setPostResponses(ArrayList<PostResponse> postResponses) {
        this.postResponses = postResponses;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(String lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public QueryResponse() {
    }

    public void add(PostResponse response) {

        postResponses.add(response);
    }
}
