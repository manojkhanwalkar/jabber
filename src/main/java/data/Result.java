package data;

public class Result {

    String result;

    String error ;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    @Override
    public String toString() {
        return "Result{" +
                "result='" + result + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
