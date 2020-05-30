package slacker;


import com.codahale.metrics.annotation.Timed;
import slacker.data.PostRequest;
import slacker.data.QueryRequest;
import slacker.data.QueryResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class SlackerResource {
    private final String template;
    private final String defaultName;



    public SlackerResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;



    }




    PostManager workManager = new PostManager();

    @POST
    @Timed
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public String work(PostRequest request) {


         workManager.process(request);

         return "submitted";


    }


    @POST
    @Timed
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public QueryResponse query(QueryRequest request) {


        return workManager.process(request);




    }










}
