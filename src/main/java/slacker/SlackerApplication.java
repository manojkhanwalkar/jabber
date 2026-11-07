package slacker;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class SlackerApplication extends Application<SlackerConfiguration> {
    public static void main(String[] args) throws Exception {
        new SlackerApplication().run(args);
    }

    @Override
    public String getName() {
        return "Slacker Application";
    }

    @Override
    public void initialize(Bootstrap<SlackerConfiguration> bootstrap) {
        // Resolve ${ENV_VAR} references (keystore path/password) in the YAML config.
        bootstrap.setConfigurationSourceProvider(new io.dropwizard.configuration.SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new io.dropwizard.configuration.EnvironmentVariableSubstitutor(true)));

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(SlackerConfiguration configuration,
                    Environment environment) {
        final SlackerResource resource = new SlackerResource(
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




}

}