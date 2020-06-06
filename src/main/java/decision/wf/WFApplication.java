package decision.wf;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import util.AppHealthCheck;


public class WFApplication extends Application<WFConfiguration> {
    public static void main(String[] args) throws Exception {
        new WFApplication().run(args);
    }

    @Override
    public String getName() {
        return "Scheduler Application";
    }

    @Override
    public void initialize(Bootstrap<WFConfiguration> bootstrap) {

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(WFConfiguration configuration,
                    Environment environment) {
        final WFResource resource = new WFResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

     /*   DiscoveryLifeCycle myManagedObject = new DiscoveryLifeCycle(Constants.BureauServiceUrl,Constants.BureauServiceType, Constants.BureauServiceHealthUrl);
        environment.lifecycle().manage(myManagedObject);

        CreditManagerFactory creditManagerFactory = CreditManagerFactory.getInstance();
        creditManagerFactory.init();*/

        environment.jersey().register(resource);
        environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

    }

}