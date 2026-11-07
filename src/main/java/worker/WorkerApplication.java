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
        // Resolve ${ENV_VAR} references (keystore path/password) in the YAML config.
        bootstrap.setConfigurationSourceProvider(new io.dropwizard.configuration.SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new io.dropwizard.configuration.EnvironmentVariableSubstitutor(true)));

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
        environment.jersey().register(new util.SharedTokenAuthFilter());
      //  environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                resource.deregisterWorker();
            }
        });


}

}