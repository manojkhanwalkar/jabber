package matcher;

import util.JSONUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;


public class ProfileGenerator {


    public static void main(String[] args) throws Exception {

        ProfileManager manager = new ProfileManager();

         generateProfiles();


         readProfiles().stream().forEach(p->{

                manager.add(p);
            CompletableFuture.runAsync(new MatchProcessor(manager,p));

                });


        Thread.sleep(5000);


        manager.matches.entrySet().stream().forEach(entry->{

            System.out.println("Matches " + entry.getValue().size() + "  " + entry.getKey() + "==> " + entry.getValue());
        });



    }


    static void generateProfiles() throws Exception
    {
       //  /home/manoj/data/matcher
        File file = new File("/home/manoj/data/matcher/profiles");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        for (int i=0;i<100;i++)
        {
            Profile.ProfileBuilder builder = Profile.builder();
            builder.name(getName(5))
                  .age(getAge(20,30))
                    .gender(getGender())
                    .height(getHeight(150,180))
                    .interests(getInterests(3))
                    .id(getId())
                    .religion(getReligion())
                    .state(getState())
                    .match(getCriteria1(builder.gender,22,26, Profile.State.CA));
                   // .match(getDefaultCriteria());

            Profile profile = builder.build();

            bufferedWriter.write(JSONUtil.toJSON(profile));
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        bufferedWriter.close();

    }


    static List<Profile>  readProfiles() throws Exception
    {
        File file = new File("/home/manoj/data/matcher/profiles");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line = bufferedReader.readLine();

        List<Profile> profiles = new ArrayList<>();
        while(line!=null) {

            Profile profile = (Profile)JSONUtil.fromJSON(line,Profile.class);

            profiles.add(profile);

            line = bufferedReader.readLine();
        }

        return profiles;

    }

    static Random random= new Random();

    public static MatchCriteria getDefaultCriteria()
    {
        return new MatchCriteria();
    }


    public static MatchCriteria getCriteria1(Profile.Gender curr, int minAge, int maxAge, Profile.State state)
    {
        MatchCriteria matchCriteria = new MatchCriteria();
        GenderCriteria genderCriteria= new GenderCriteria(curr,true);
        matchCriteria.setGenderCriteria(genderCriteria);

        AgeCriteria ageCriteria = new AgeCriteria(minAge,maxAge);

        matchCriteria.setAgeCriteria(ageCriteria);

        ArrayList<Profile.State> states = new ArrayList<>();
        states.add(state);
        StateCriteria stateCriteria = new StateCriteria(states);

        matchCriteria.setStateCriteria(stateCriteria);


        return matchCriteria;
    }



    public static String getName(int length)
    {
        StringBuilder builder = new StringBuilder(length);

        for (int i=0;i<length;i++)
        {
            char c = (char)((int)'a' + random.nextInt(26));
            builder.append(c);

        }

        return builder.toString();

    }

    public static Profile.Gender getGender()
    {
       int toss = random.nextInt(2);
       if (toss==0)
           return Profile.Gender.F;
       else
           return Profile.Gender.M;
    }

    public static int getAge(int min , int max)
    {
        return min + random.nextInt(max-min);
    }

    public static Profile.State getState()
    {

        int num = Profile.State.values().length;

        num = random.nextInt(num);

        return Profile.State.values()[num];
    }

    public static int getHeight(int min , int max)
    {
        return min + random.nextInt(max-min);
    }

    public static Profile.Religion getReligion()
    {

        int num = Profile.Religion.values().length;

        num = random.nextInt(num);

        return Profile.Religion.values()[num];
    }

    public static int getId()
    {
        return  random.nextInt(Integer.MAX_VALUE);
    }



    public static ArrayList<Profile.Interests> getInterests(int count)
    {

        ArrayList<Profile.Interests> interests = new ArrayList<>();
        for (int i=0;i<count;i++)
        {
            int num = Profile.Interests.values().length;

            num = random.nextInt(num);

            if (!interests.contains(Profile.Interests.values()[num]))
                 interests.add(Profile.Interests.values()[num]);

        }

        return interests;

    }


}
