import com.aventstack.extentreports.*;
import org.helper.ConfigJson.ConfigLoaderJson;
import org.helper.ConfigJson.ConfigUtilJson;
import org.helper.UserUtilsHelper;
import org.helper.ConfigurationReaderProperties;
import org.helper.MockServer;
import org.helper.Reports;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;


public class TestCases {

    private MockServer mockServer;
    ConfigLoaderJson config = ConfigUtilJson.readConfig("src/main/resources/params.json");
    static int port = Integer.parseInt(ConfigurationReaderProperties.get("wireMock_port"));
    static String baseUriPath= ConfigurationReaderProperties.get("base_uri");
    static String createUser= ConfigurationReaderProperties.get("createUser_path");

    public TestCases() throws IOException {
    }

    @BeforeClass
    public void setUp() {
        mockServer = new MockServer();
        mockServer.startServer();
        Reports.setupReports();
    }



    @Test(priority = 0)
    public void createUserTest() throws Exception {
        ExtentTest report = Reports.createTest("Create user test", "The test created new user");
        mockServer = new MockServer();
        mockServer.createUser();
        try {
            String serverUrl = baseUriPath+port;
            String response = UserUtilsHelper.createUser(serverUrl, config.getId(), config.getName(), config.getEmail());
            System.out.println(response);
            Assert.assertTrue(response.contains("User created successfully"));
            report.pass("Test passed");
        }
        catch (Exception e) {
            report.fail("Request failed: " + e.getMessage());
        }
    }
    @Test
    public void getUserTest() throws Exception {
        ExtentTest report = Reports.createTest("Getting user test", "The test getting user");
        mockServer = new MockServer();
        mockServer.getUser(config.getId(), config.getName(), config.getEmail());
        try {
            String serverUrl = baseUriPath+port;
            String response = UserUtilsHelper.getUser(serverUrl + "/getUser/" + config.getId());
            Assert.assertTrue(response.contains("\"id\": \""+config.getId()+"\""), "User ID not found in response");
            Assert.assertTrue(response.contains("\"name\": \""+config.getName()+"\""), "User name not found in response");
            Assert.assertTrue(response.contains("\"email\": \""+config.getEmail()+"\""), "Email not found in response");
            System.out.println(response);
            Assert.assertTrue(response.contains("Getting user successfully"));
            report.pass("Test passed");
        }
        catch (Exception e) {
            report.fail("Request failed: " + e.getMessage());
        }
    }
    @Test
    public void updateUserTest() throws Exception {
        ExtentTest report = Reports.createTest("Update user test", "The test updated user");
        mockServer = new MockServer();
        mockServer.updateUser();
        try {
            String serverUrl = baseUriPath+port;
            String response = UserUtilsHelper.updatedUser(serverUrl, config.getId(), config.getName(), config.getEmail());
            System.out.println(response);
            Assert.assertTrue(response.contains("User updated successfully"));
            report.pass("Test passed");
        }
        catch (Exception e) {
            report.fail("Request failed: " + e.getMessage());
        }
    }
    @Test
    public void deleteUserTest() throws Exception {
        ExtentTest report = Reports.createTest("Delete user test", "The test deleted user");
        mockServer = new MockServer();
        mockServer.deleteUser();
        try {
            String serverUrl = baseUriPath+port;
            String response = UserUtilsHelper.deletedUser(serverUrl, config.getId());
            System.out.println(response);
            Assert.assertTrue(response.contains("User deleted successfully"));
            report.pass("Test passed");
        }
        catch (Exception e) {
            report.fail("Request failed: " + e.getMessage());
        }
    }

    @Test
    public void nonExistentEndpointTest() throws Exception {
        ExtentTest report = Reports.createTest("Getting user non existent endpoint test", "The test getting user non existent endpoint");
        mockServer = new MockServer();
        mockServer.getUserNotExist();
        try {
            String serverUrl = baseUriPath+port;
            String response = UserUtilsHelper.getUserEndPoint(serverUrl, "/non-existent-endpoint");
            System.out.println(response);
            Assert.assertTrue(response.contains("Endpoint does not exist"), "Error message is not correct");
            Assert.assertTrue(response.contains("Status: 404"), "Status code is not 404");
            report.pass("Test passed");
        }
        catch (Exception e) {
            report.fail("Request failed: " + e.getMessage());
        }
    }
    @AfterClass
    public void tearDown() {
        mockServer.stopServer();
        Reports.flushReports();
    }
}

