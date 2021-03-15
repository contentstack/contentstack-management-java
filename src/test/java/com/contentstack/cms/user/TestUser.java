package com.contentstack.cms.user;

import com.contentstack.cms.core.CSProtocol;
import com.contentstack.cms.Contentstack;
import lombok.var;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestUser {

    @Test
    public void testClientCustomBuilder() {
        var client = new Contentstack.Client("cs444444444444")
                .setHostname("")
                .setProtocol(CSProtocol.http)
                .setTimeout(30)
                .setVersion("v3");
        try {
            client.user().updateUser().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    @Test
//    public void testClientBuilder() {
//        var client =  new contentstack.Client("cs63990eb0c84ccde4b12b6a73").build();
//        Response<UserModel> response = client.user().getUser().execute();
//        if (response.isSuccessful()){
//            System.out.println(response.isSuccessful());
//        }else {
//            System.out.println(response.errorBody().charStream());
//        }
//    }
}
