package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GlobalFieldsAPITest {

    private static GlobalField globalField;


    @BeforeAll
    static void setup() throws IOException {

        Dotenv cred = Dotenv.load();
        String username = cred.get("username");
        String password = cred.get("password");
        String globalFiledUid = cred.get("authToken");
        Contentstack contentstack = new Contentstack.Builder().build();
        contentstack.login(username, password);
        globalField = contentstack.stack().globalField(globalFiledUid);
    }

    @Order(1)
    @Test
    void testGlobalFieldFind() throws IOException {
        Response<ResponseBody> response = globalField.find().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void testGlobalFieldFetch() throws IOException {
        Response<ResponseBody> response = globalField.fetch().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void testCreate() throws IOException {
        Response<ResponseBody> response = globalField.create(new JSONObject()).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void testUpdate() throws IOException {
        Response<ResponseBody> response = globalField.update(new JSONObject()).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void testDelete() throws IOException {
        Response<ResponseBody> response = globalField.delete().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    @Disabled
    void testImport() throws IOException {
        JSONObject globalFieldBody = new JSONObject();
        JSONObject otherDetails = new JSONObject();
        otherDetails.put("title", "technology");
        globalFieldBody.put("global_field", otherDetails);
        Response<ResponseBody> response = globalField.imports(globalFieldBody).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void testExport() throws IOException {
        Response<ResponseBody> response = globalField.export().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }

    }

}
