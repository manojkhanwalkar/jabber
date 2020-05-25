package data;

public class SubmitResponse {

    String name;
    String id;

    public SubmitResponse(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public SubmitResponse() {


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SubmitResponse{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}


