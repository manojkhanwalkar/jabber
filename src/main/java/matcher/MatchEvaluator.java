package matcher;

public class MatchEvaluator implements Criteria {

    @Override
    public boolean evaluate(Profile profile1, Profile profile2) {

        // evaluate profile one against profile 2 and then reverse .

        MatchCriteria matchCriteria = profile1.getMatchCriteria();
        AgeCriteria ageCriteria = matchCriteria.getAgeCriteria();

        boolean result1 = ageCriteria.evaluate(profile1);

        matchCriteria = profile2.getMatchCriteria();
        ageCriteria = matchCriteria.getAgeCriteria();

        boolean result2 = ageCriteria.evaluate(profile2);

        return result1&&result2;

//TODO - add more match criteria here .

//TODO - come up with a scoring scheme to allow partial matches

    }
}
