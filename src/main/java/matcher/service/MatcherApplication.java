package matcher.service;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class MatcherApplication extends Application<MatcherConfiguration> {
    public static void main(String[] args) throws Exception {
        new MatcherApplication().run(args);
    }

    @Override
    public String getName() {
        return "Matcher Application";
    }

    @Override
    public void initialize(Bootstrap<MatcherConfiguration> bootstrap) {

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(MatcherConfiguration configuration,
                    Environment environment) {
        final MatcherResource resource = new MatcherResource(
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