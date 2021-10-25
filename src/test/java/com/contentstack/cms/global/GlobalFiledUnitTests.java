package com.contentstack.cms.global;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

 class GlobalFiledUnitTests {

    protected String globalFiledUid = "just@fake";
    protected static String globalAuthtoken = Dotenv.load().get("authToken");
    protected static String apiKey = Dotenv.load().get("api_key");
    protected static String managementToken = Dotenv.load().get("auth_token");
    protected static GlobalField globalField;

    @BeforeAll
    static void setup() {
        globalField = new Contentstack.Builder()
                .setAuthtoken(globalAuthtoken)
                .build()
                .globalField(apiKey, managementToken);
    }


    @Test
    void globalFetch() {
        globalField.fetch();
        Assertions.assertTrue(true);
    }
//
//    @Test
//    void globalSingle() {
//        globalField.single(globalFiledUid);
//    }
//
//    @Test
//    void globalCreate() {
//        globalField.create(globalFiledUid);
//    }
//
//    @Test
//    void globalUpdate() {
//        globalField.update(globalFiledUid, "");
//    }
//
//    @Test
//    void globalDelete() {
//        globalField.delete(globalFiledUid);
//    }
//
//
//    @Test
//    void globalImport() {
//        globalField.imports();
//    }
//
//    @Test
//    void globalExport() {
//        globalField.export(globalFiledUid);
//    }
}
