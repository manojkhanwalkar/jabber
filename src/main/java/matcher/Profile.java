package matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Profile {

    public enum Religion { C, M, H , B };

    public enum Interests { sports, movies , cars, technology, finance , gardening};

    public enum State {  PA , NJ , NY , FL, WA , CA};

    public enum Gender { M , F }

    String name;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    int id;
    int age;
    int height;
    Religion religion;
    State state;
    Gender gender;

    MatchCriteria matchCriteria;

    ArrayList<Interests> interests = new ArrayList<>();

    public Profile(String name, int id, int age, int height, Religion religion, State state, Gender gender, ArrayList<Interests> interests, MatchCriteria matchCriteria) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.height = height;
        this.religion = religion;
        this.state = state;
        this.gender = gender;
        this.interests = interests;
        this.matchCriteria = matchCriteria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Profile() {
    }

    public void addInterest(Interests interest)
    {
        interests.add(interest);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public ArrayList<Interests> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Interests> interests) {
        this.interests = interests;
    }

    public static ProfileBuilder builder()
    {
        return new ProfileBuilder();
    }


    public MatchCriteria getMatchCriteria() {
        return matchCriteria;
    }

    public void setMatchCriteria(MatchCriteria matchCriteria) {
        this.matchCriteria = matchCriteria;
    }

    static class ProfileBuilder
    {

        String name;
        int id;
        int age;
        int height;
        Religion religion;
        State state;
        Gender gender;

        ArrayList<Interests> interests = new ArrayList<>();

        MatchCriteria matchCriteria;


        public Profile build()
        {
            return new Profile(name,id,age,height,religion,state,gender,interests,matchCriteria);
        }

        public ProfileBuilder name(String name)
        {
            this.name = name;
            return this;
        }

        public ProfileBuilder id(int id)
        {
            this.id = id;
            return this;
        }

        public ProfileBuilder age(int age)
        {
            this.age = age;
            return this;
        }


        public ProfileBuilder height(int height)
        {
            this.height = height;
            return this;
        }

        public ProfileBuilder religion(Religion religion)
        {
            this.religion = religion;
            return this;
        }

        public ProfileBuilder state(State state)
        {
            this.state = state;
            return this;
        }



        public ProfileBuilder gender(Gender gender)
        {
            this.gender = gender;
            return this;
        }



        public ProfileBuilder interests(ArrayList<Interests> interests)
        {
            this.interests = interests;
            return this;
        }


        public ProfileBuilder match(MatchCriteria matchCriteria)
        {
            this.matchCriteria = matchCriteria;
            return this;
        }


    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                ", height=" + height +
                ", religion=" + religion +
                ", state=" + state +
                ", gender=" + gender +
                ", matchCriteria=" + matchCriteria +
                ", interests=" + interests +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
