package vfs.service;


import com.codahale.metrics.annotation.Timed;
import matcher.Profile;
import matcher.ProfileManager;
import matcher.Profiles;
import vfs.TransactionData;
import vfs.VFSManager;
import vfs.VelocityStats;
import vfs.VelocityStatsList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class VFSResource {
    private final String template;
    private final String defaultName;



    public VFSResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;

        vfsManager.init();

    }


    VFSManager vfsManager = new VFSManager();




    @POST
    @Timed
    @Path("/transaction")
    @Produces(MediaType.APPLICATION_JSON)
    public VelocityStatsList create(TransactionData transactionData) {


        return vfsManager.process(transactionData);


    }










}
