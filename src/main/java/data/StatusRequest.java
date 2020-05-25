package data;

import java.util.ArrayList;
import java.util.List;

public class StatusRequest {


    ArrayList<String> jobIds = new ArrayList<>();

    String client;


    public ArrayList<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(ArrayList<String> jobIds) {
        this.jobIds = jobIds;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public StatusRequest(ArrayList<String> jobIds, String client) {
        this.jobIds = jobIds;
        this.client = client;
    }

    public StatusRequest() {
    }
}
