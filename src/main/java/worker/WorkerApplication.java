package worker;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class WorkerApplication extends Application<WorkerConfiguration> {
    public static void main(String[] args) throws Exception {
        new WorkerApplication().run(args);
    }

    @Override
    public String getName() {
        return "Scheduler Application";
    }

    @Override
    public void initialize(Bootstrap<WorkerConfiguration> bootstrap) {

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(WorkerConfiguration configuration,
                    Environment environment) {
        final WorkerResource resource = new WorkerResource(
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