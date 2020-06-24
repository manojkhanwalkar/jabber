package matcher.service;


import com.codahale.metrics.annotation.Timed;
import matcher.Profile;
import matcher.ProfileManager;
import matcher.Profiles;
import slacker.PostManager;
import slacker.data.PostRequest;
import slacker.data.QueryRequest;
import slacker.data.QueryResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MatcherResource {
    private final String template;
    private final String defaultName;



    public MatcherResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;



    }

    ProfileManager manager = new ProfileManager();


    @POST
    @Timed
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String create(Profile profile) {


        manager.add(profile);
         return "submitted";


    }


    @POST
    @Timed
    @Path("/match")
    @Produces(MediaType.APPLICATION_JSON)
    public Profiles query(String profileId) {


        //System.out.println(profileId);
        return manager.get(profileId);




    }










}
