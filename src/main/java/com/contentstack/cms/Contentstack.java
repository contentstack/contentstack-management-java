package com.contentstack.cms;

import com.contentstack.cms.core.CSProtocol;
import com.contentstack.cms.core.Constants;
import com.contentstack.cms.core.HeaderInterceptor;
import com.contentstack.cms.user.User;
import com.contentstack.cms.user.model.UserModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;


public class Contentstack {

    public static class Client {

        private CSProtocol protocol = CSProtocol.https;
        private String hostname = Constants.HOST;
        private String port = Constants.PORT;
        private String version = Constants.VERSION;
        private String baseURL;
        private int timeout = Constants.TIMEOUT;
        private Retrofit instance;


        public Client(String authtoken) {
            if (authtoken==null || authtoken.isEmpty()){
                throw new NullPointerException("authtoken can not be null or empty");
            }
            // Even when build method not called return client instance
            initialiseClient();
        }


        private Client(Retrofit instance) {
            this.instance=instance;
        }

        public Client setProtocol(CSProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Client setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Client setPort(String port) {
            this.port = port;
            return this;
        }

        public Client setVersion(String version) {
            this.version = version;
            return this;
        }

        public Client setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        private Client initialiseClient(){
            String protocol = this.protocol.name().equals("https")?"https://":"http://";
            this.baseURL = protocol+this.hostname+"/"+this.version+"/";
            okHttpClient();
            Retrofit instance = client();
            return new Client(instance);
        }

        public Client build(){
            return initialiseClient();
        }


        private Retrofit client(){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            this.instance = new Retrofit.Builder()
                    .baseUrl(this.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient())
                    .build();
            return this.instance;
        }

        private OkHttpClient okHttpClient(){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            return new OkHttpClient.Builder()
                    .readTimeout(this.timeout, TimeUnit.SECONDS)
                    .connectTimeout(this.timeout, TimeUnit.SECONDS)
                    .pingInterval(200, TimeUnit.MILLISECONDS)
                    .addInterceptor(new HeaderInterceptor())
                    .retryOnConnectionFailure(true)
                    .build();
        }


        public User user(){
            return new User(this.instance);
        }

    }


}
