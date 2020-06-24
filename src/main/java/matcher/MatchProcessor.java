package matcher;

public class MatchProcessor implements Runnable {

    ProfileManager profileManager ;
    Profile profile1;


    public MatchProcessor(ProfileManager profileManager, Profile profile1) {
        this.profileManager = profileManager;
        this.profile1 = profile1;

    }

    public void run()
    {
        profileManager.profiles.values().stream().forEach(profile2->{

            if (MatchEvaluator.evaluate(profile1,profile2))
            {
                profileManager.add(profile1,profile2);
            }

        });


    }
}
