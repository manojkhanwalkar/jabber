package matcher;

public class MatchCriteria {

    Profile profile;



    AgeCriteria ageCriteria;

    public AgeCriteria getAgeCriteria() {
        return ageCriteria;
    }

    public void setAgeCriteria(AgeCriteria ageCriteria) {
        this.ageCriteria = ageCriteria;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
