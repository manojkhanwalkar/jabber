package scheduler;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;



public class SchedulerApplication extends Application<SchedulerConfiguration> {
    public static void main(String[] args) throws Exception {
        new SchedulerApplication().run(args);
    }

    @Override
    public String getName() {
        return "Scheduler Application";
    }

    @Override
    public void initialize(Bootstrap<SchedulerConfiguration> bootstrap) {

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(SchedulerConfiguration configuration,
                    Environment environment) {
        final SchedulerResource resource = new SchedulerResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

     /*   DiscoveryLifeCycle myManagedObject = new DiscoveryLifeCycle(Constants.BureauServiceUrl,Constants.BureauServiceType, Constants.BureauServiceHealthUrl);
        environment.lifecycle().manage(myManagedObject);

        CreditManagerFactory creditManagerFactory = CreditManagerFactory.getInstance();
        creditManagerFactory.init();*/

        environment.jersey().register(resource);
      //  environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

    }

}