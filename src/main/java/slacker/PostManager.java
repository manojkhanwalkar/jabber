package slacker;

import slacker.data.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class PostManager {

    Map<String, Deque<Post>> channelPosts = new HashMap<>();

    public void process(PostRequest request) {

            Post post = Post.create(request);

            Deque<Post> posts = channelPosts.computeIfAbsent(post.getChannelId(), (k)->{

                return new ConcurrentLinkedDeque<>();


            });

            posts.addLast(post);




    }

    public QueryResponse process(QueryRequest request) {


        QueryResponse queryResponse = new QueryResponse();

        Stack<PostResponse> tmp = new Stack<>();

        String id = request.getChannelId();
        String messageId = request.getLastMessageId();
        String lastMessageId = messageId;  // if none found , then lastMessageId is the same as the request.

        Deque<Post> posts = channelPosts.get(id);
        if (posts!=null)
        {

            lastMessageId = posts.peekLast().getMessageId();
            Iterator<Post> iterator = posts.descendingIterator();


            while(iterator.hasNext())
            {
                Post curr = iterator.next();
                String currMessageId = curr.getMessageId();
                if(currMessageId.equals(messageId)) {
                    break;
                }

                PostResponse response = Post.create(curr);
                tmp.push(response);

            }
        }

        queryResponse.setLastMessageId(lastMessageId);
        while(!tmp.isEmpty())
        {
            queryResponse.add(tmp.pop());
        }

        return queryResponse;

    }
}
