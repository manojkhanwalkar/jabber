package data;

import java.util.List;

public class StatusRequest {


    List<String> jobIds;

    String client;


    public List<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<String> jobIds) {
        this.jobIds = jobIds;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public StatusRequest(List<String> jobIds, String client) {
        this.jobIds = jobIds;
        this.client = client;
    }

    public StatusRequest() {
    }
}
