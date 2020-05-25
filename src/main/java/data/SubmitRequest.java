package data;

public class SubmitRequest {

    String base64Jar;
    String client;
    String jobClassName;

    public SubmitRequest(String base64Jar, String client, String jobClassName) {
        this.base64Jar = base64Jar;
        this.client = client;
        this.jobClassName = jobClassName;
    }

    public SubmitRequest() {
    }


    public String getBase64Jar() {
        return base64Jar;
    }

    public void setBase64Jar(String base64Jar) {
        this.base64Jar = base64Jar;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }
}
