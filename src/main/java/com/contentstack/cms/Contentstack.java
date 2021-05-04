package com.contentstack.cms;

import com.contentstack.cms.core.Constants;
import com.contentstack.cms.core.HeaderInterceptor;
import com.contentstack.cms.user.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Contentstack {

    protected String host;
    protected String port;
    protected String version;
    protected int timeout;
    protected String authtoken;
    protected Retrofit instance;


    public Contentstack(Builder builder) {
        this.host = builder.hostname;
        this.port = builder.port;
        this.version = builder.version;
        this.timeout = builder.timeout;
        this.authtoken = builder.authtoken;
        this.instance = builder.instance;;
    }


    /**
     * <b>[Note]:</b>
     * Before executing any calls, retrieve the authtoken by authenticating
     * yourself via the Log in call of User Session. The authtoken is returned in
     * the 'Response' body of the Log in call and is mandatory in all of the calls.
     * <p><b>Example:</b> blt3cecf75b33bb2ebe
     * <p>
     * All accounts registered with Contentstack are known as Users.
     * A stack can have many users with varying permissions and roles
     * <p>To perform User operations first get User instance like below.
     * <p>
     * <b>Example:</b>
     * <pre>
     *  Client client = new Client.Builder().setAuthtoken("blt3cecf75b33bb2ebe").build();
     *  User userInstance = client.user();
     * </pre>
     * <br>
     * <b>OR: </b>
     * <pre>
     *  Client client = new Client.Builder().build();
     *  User userInstance = client.user();
     * </pre>
     * <p>
     *
     * @return User
     */
    public User user() {
        return new User(this.instance);
    }


    // Static class Builder
    public static class Builder {

        private String authtoken;  // authtoken for client
        private Retrofit instance; // client instance
        private String hostname = Constants.HOST; // Default Host for Contentstack API (default: api.contentstack.io)
        private String port = Constants.PORT; // Default PORT for Contentstack API
        private String version = Constants.VERSION; // Default Version for Contentstack API
        private int timeout = Constants.TIMEOUT; // Default timeout 30 seconds
        private String baseURL = Constants.BASE_URL; // Default base url for contentstack


        public Builder() {

        }

        private void setBaseUrl(String baseURL) {
            this.baseURL = baseURL;
        }


        /**
         * Set host for client instance
         *
         * @param hostname - host for the Contentstack Client
         * @return Client
         */
        public Builder setHost(String hostname) {
            this.hostname = hostname;
            return this;
        }


        /**
         * Set port for client instance
         *
         * @param port - port for the Contentstack Client
         * @return Client
         */
        public Builder setPort(String port) {
            this.port = port;
            return this;
        }


        /**
         * Set version for client instance
         *
         * @param version - version for the Contentstack Client
         * @return Client
         */
        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }


        /**
         * Set timeout for client instance
         *
         * @param timeout - timeout for the Contentstack Client
         * @return Client
         */
        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }


        /**
         * Sets authtoken for the client
         *
         * @param authtoken authtoken for the client
         * @return Contentstack
         */
        public Builder setAuthtoken(String authtoken) {
            this.authtoken = authtoken;
            return this;
        }


        /**
         * Create Client instance for Contentstack
         * @return Contentstack
         */
        public Contentstack build() {
            new Builder();
            String BASE_URL = Constants.PROTOCOL + "://" + this.hostname + "/" + version + "/";
            setBaseUrl(BASE_URL);
            this.instance = new Retrofit.Builder()
                    .baseUrl(this.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient())
                    .build();

            return new Contentstack(this);
        }


        private OkHttpClient httpClient() {
            // Add more custom fields here
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            return new OkHttpClient.Builder()
                    .addInterceptor(new HeaderInterceptor(this.authtoken))
                    .retryOnConnectionFailure(true)
                    .build();
        }

    }
}
