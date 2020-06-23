package matcher;

public class MatchCriteria {



    AgeCriteria ageCriteria = AgeCriteria.any();

    GenderCriteria genderCriteria= GenderCriteria.any();

    HeightCriteria heightCriteria= HeightCriteria.any();

    InterestCriteria interestCriteria = InterestCriteria.any();

    ReligionCriteria religionCriteria = ReligionCriteria.any();

    StateCriteria stateCriteria= StateCriteria.any();

    public GenderCriteria getGenderCriteria() {
        return genderCriteria;
    }

    public void setGenderCriteria(GenderCriteria genderCriteria) {
        this.genderCriteria = genderCriteria;
    }

    public HeightCriteria getHeightCriteria() {
        return heightCriteria;
    }

    public void setHeightCriteria(HeightCriteria heightCriteria) {
        this.heightCriteria = heightCriteria;
    }

    public InterestCriteria getInterestCriteria() {
        return interestCriteria;
    }

    public void setInterestCriteria(InterestCriteria interestCriteria) {
        this.interestCriteria = interestCriteria;
    }

    public ReligionCriteria getReligionCriteria() {
        return religionCriteria;
    }

    public void setReligionCriteria(ReligionCriteria religionCriteria) {
        this.religionCriteria = religionCriteria;
    }

    public StateCriteria getStateCriteria() {
        return stateCriteria;
    }

    public void setStateCriteria(StateCriteria stateCriteria) {
        this.stateCriteria = stateCriteria;
    }

    public AgeCriteria getAgeCriteria() {
        return ageCriteria;
    }

    public void setAgeCriteria(AgeCriteria ageCriteria) {
        this.ageCriteria = ageCriteria;
    }



}
