package data;

public class SubmitRequest {

    String base64Jar;
    String client;

    public SubmitRequest(String base64Jar, String client) {
        this.base64Jar = base64Jar;
        this.client = client;
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
}
