package com.contentstack.cms.core;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.user.model.UserModel;
import lombok.var;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

public class TestContentstack {


//    @Test
//    public void testClientCustomBuilder() throws IOException {
//        var client =  new Contentstack.Client("cs63990eb0c84ccde4b12b6a73")
//                .setHostname("api.contentstack.io")
//                .setProtocol(CSProtocol.https)
//                .setTimeout(300)
//                .setVersion("v3").build();
//        Response<UserModel> response = client.user().getUser().execute();
//        if (response.isSuccessful()){
//            System.out.println(response.isSuccessful());
//        }else {
//            System.out.println(response.errorBody());
//        }
//    }
//
//
//    @Test
//    public void testClientBuilder() throws IOException {
//        var client =  new Contentstack.Client("cs63990eb0c84ccde4b12b6a73").build();
//        Response<UserModel> response = client.user().getUser().execute();
//        if (response.isSuccessful()){
//            System.out.println(response.isSuccessful());
//        }else {
//            System.out.println(response.errorBody().charStream());
//        }
//    }


    @Test
    public void testClientDefault() {
//        var client =  contentstack.Client("authToken")
//        Response<User> response = client.user().getUser().execute();
//        if (response.isSuccessful()){
//            System.out.println(response.isSuccessful());
//        }else {
//            System.out.println(response.errorBody().charStream());
//        }
    }

}
