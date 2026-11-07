package util;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Properties;

public class Connection {

    String target;

    HttpClient client ;





    // Hostname verification is no longer disabled globally. For local development with a
    // self-signed certificate whose name does not match, start the JVM with
    // -Djdk.internal.httpclient.disableHostnameVerification=true explicitly.
    private void initConnectionProps()
    {
    }

    private static HttpRequest.Builder withAuth(HttpRequest.Builder builder)
    {
        String token = SharedTokenAuthFilter.clientToken();
        if (token != null && !token.isEmpty()) {
            builder.header(SharedTokenAuthFilter.HEADER, token);
        }
        return builder;
    }

    public Connection(String target)
    {
        this.target = target;
        initConnectionProps();
        client = HttpClient.newHttpClient();
    }



    public String sendSimple(String request,String action)
    {
        try {
            return post(request,action);
        } catch (Exception e) {
          //  e.printStackTrace();

            return null;
        }
    }




    private String post(String str , String url) throws Exception
    {

        HttpRequest httprequest = withAuth(HttpRequest.newBuilder())
                .uri(URI.create(target + url))
                .header("Content-Type", "application/json")
               //  .header("HMAC" , HMACUtil.calculateHMAC(str,HmacKey))
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .timeout(Duration.ofMinutes(1))
                .build();


        HttpResponse<String> response =
                client.send(httprequest, HttpResponse.BodyHandlers.ofString());

        return response.body();


    }



    public String get( String url)
    {

        try {
            HttpRequest httprequest = withAuth(HttpRequest.newBuilder())
                    .uri(URI.create(target + url))
                    .GET()
                    .timeout(Duration.ofMinutes(1))
                    .build();


            HttpResponse<String> response =
                    client.send(httprequest, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception ex)
        {
            return null;
        }


    }




}
