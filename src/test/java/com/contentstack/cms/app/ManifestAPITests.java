package com.contentstack.cms.app;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.organization.Organization;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Retrofit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManifestAPITests {

    private static Organization organization;
    private final static String authtoken = Dotenv.load().get("authToken");
    private final static String organizationUid = Dotenv.load().get("organizationUid");


    private JSONObject theJSONBody(@NotNull String _body) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(_body);
        } catch (ParseException e) {
            System.out.println("Json Reading Error: " + e.getLocalizedMessage());
            return null;
        }
    }

    @BeforeAll
    public static void setUp() {
        organization = new Contentstack.Builder().setAuthtoken(authtoken).build().organization(organizationUid);
    }


    @Test
    @Order(1)
    void testConstructorWithRetrofitClientAndAuthtoken() {
        // create retrofit client
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.contentstack.io/v3/").build();
        assertEquals("https://api.contentstack.io/v3/", retrofit.baseUrl().toString());
    }

    @Test
    @Order(2)
    void testOrganizationGetAllRelativeUrl() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("/v3/organizations", requestInfo.url().encodedPath());
    }

    //////////////////////////////
    @Test
    @Order(3)
    void testGetAllMethod() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    @Order(4)
    void testGetAllBaseUrl() {
        organization.addParam("asc", false);
        organization.addParam("typehead", "contentstack");
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        organization.addParam("include_count", true);
        organization.addParam("desc", true);
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations?asc=false&typehead=contentstack&limit=4&skip=4&include_count=true&desc=true",
                requestInfo.url().toString());
    }

    @Test
    @Order(5)
    void testGetAllEncodedPath() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("/v3/organizations",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(6)
    void testFetch() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("asc=false&typehead=contentstack&limit=4&skip=4&include_count=true&desc=true", requestInfo.url().query());
    }


}
