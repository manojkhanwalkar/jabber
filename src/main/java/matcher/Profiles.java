package matcher;

import java.util.ArrayList;

public class Profiles {

    ArrayList<Profile> profiles = new ArrayList<>();

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }


    public void add(Profile profile) {

        profiles.add(profile);
    }
}
