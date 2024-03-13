package org.helper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;


public class UserUtilsHelper {

    public static String createUser(String serverUrl, String id, String name, String email) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(serverUrl + "/createUser");
            String json = String.format("{\"id\": \"%s\", \"name\": \"%s\", \"email\": \"%s\"}", id, name, email);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }
    public static String deletedUser(String serverUrl, String id) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete request = new HttpDelete(serverUrl + "/deleteUser/" + id);
            var response = httpClient.execute(request);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            return EntityUtils.toString(response.getEntity());
        }
    }
    public static String getUser(String serverUrl) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(serverUrl);
            var response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }
    public static String updatedUser(String serverUrl, String id, String name, String email) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(serverUrl + "/updateUser");
            String json = String.format("{\"id\": \"%s\", \"name\": \"%s\", \"email\": \"%s\"}", id, name, email);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }
    public static String getUserEndPoint(String serverUrl, String endpoint) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(serverUrl + endpoint);
            var response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity()) + " Status: " + response.getStatusLine().getStatusCode();

        }
    }
}