package matcher;

public class MatchEvaluator  {


    public static boolean evaluate(Profile profile1, Profile profile2) {

        // evaluate profile one against profile 2 and then reverse .

        MatchCriteria matchCriteria = profile1.getMatchCriteria();

        boolean result1 = evaluate(matchCriteria, profile2);

        matchCriteria = profile2.getMatchCriteria();

        boolean result2 = evaluate(matchCriteria, profile1);

        return result1&&result2;



    }

    private static  boolean evaluate(MatchCriteria matchCriteria , Profile other)
    {
        AgeCriteria ageCriteria = matchCriteria.getAgeCriteria();
        GenderCriteria genderCriteria = matchCriteria.getGenderCriteria();
        StateCriteria stateCriteria = matchCriteria.getStateCriteria();
        InterestCriteria interestCriteria = matchCriteria.getInterestCriteria();
        ReligionCriteria religionCriteria = matchCriteria.getReligionCriteria();
        HeightCriteria heightCriteria = matchCriteria.getHeightCriteria();


        return ageCriteria.evaluate(other) && genderCriteria.evaluate(other)
                && stateCriteria.evaluate(other)
                && interestCriteria.evaluate(other)
                && religionCriteria.evaluate(other)
                && heightCriteria.evaluate(other);
    }
}
