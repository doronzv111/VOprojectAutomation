package org.helper;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {
    static int port = Integer.parseInt(ConfigurationReaderProperties.get("wireMock_port"));
    static int ok200 = Integer.parseInt(ConfigurationReaderProperties.get("status200Ok"));
    private WireMockServer wireMockServer;

    public void startServer() {
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
    }
    public void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
    public void createUser() {
        configureFor(ConfigurationReaderProperties.get("localHost_name"), port);
        stubFor(post(urlEqualTo("/createUser"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(ok200)
                        .withBody("{\"message\": \"User created successfully\"}")));
    }
    public void getUser(String id, String name, String email) {
        configureFor(ConfigurationReaderProperties.get("localHost_name"), port);
        stubFor(get(urlEqualTo("/getUser/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(ok200)
                        .withBody("{\"id\": \""+id+"\", \"name\": \""+name+"\", \"email\": \""+email+"\"}")));
    }
    public void getUserNotExist() {
        configureFor(ConfigurationReaderProperties.get("localHost_name"), port);
        stubFor(get(urlEqualTo("/non-existent-endpoint"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)
                        .withBody("{\"error\": \"Endpoint does not exist\"}")));
    }
    public void updateUser() {
        configureFor(ConfigurationReaderProperties.get("localHost_name"), port);
        stubFor(put(urlEqualTo("/updateUser"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(ok200)
                        .withBody("{\"message\": \"User updated successfully\"}")));
    }
    public void deleteUser() {
        stubFor(delete(urlMatching("/deleteUser/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(ok200)
                        .withBody("{\"message\": \"User deleted successfully\"}")));
    }

}