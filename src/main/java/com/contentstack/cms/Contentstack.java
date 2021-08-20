package com.contentstack.cms;

import com.contentstack.cms.core.Constants;
import com.contentstack.cms.core.HeaderInterceptor;
import com.contentstack.cms.user.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.Proxy;

/**
 * The type Contentstack.
 */
public class Contentstack {

    protected String host;
    protected String port;
    protected String version;
    protected int timeout;
    protected String authtoken;
    protected Retrofit instance;
    protected Boolean retryOnFailure = true;
    protected Proxy proxy;

    /**
     * Instantiates a new Contentstack.
     *
     * @param builder the builder
     */
    public Contentstack(Builder builder) {
        this.host = builder.hostname;
        this.port = builder.port;
        this.version = builder.version;
        this.timeout = builder.timeout;
        this.authtoken = builder.authtoken;
        this.instance = builder.instance;
        this.retryOnFailure = builder.retryOnFailure;
        this.proxy = builder.proxy;
    }


    /**
     * <b>[Note]:</b>
     * Before executing any calls, retrieve the authtoken by authenticating
     * yourself via the Log in call of User Session. The authtoken is returned in
     * the 'Response' body of the Log in call and is mandatory in all of the calls.
     * <p><b>Example:</b>
     * <p>
     * All accounts registered with Contentstack are known as Users.
     * A stack can have many users with varying permissions and roles
     * <p>To perform User operations first get User instance like below.
     * <p>
     * <b>Example:</b>
     * <pre>
     *  Client client = new Client.Builder().setAuthtoken("authtoken").build();
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
     * @return User user
     */


    public User user() {
        return new User(this.instance);
    }


    /**
     * The type Builder.
     */
    public static class Builder {
        public Proxy proxy;
        private String authtoken;  // authtoken for client
        private Retrofit instance; // client instance
        private String hostname = Constants.HOST; // Default Host for Contentstack API (default: api.contentstack.io)
        private String port = Constants.PORT; // Default PORT for Contentstack API
        private String version = Constants.VERSION; // Default Version for Contentstack API
        private int timeout = Constants.TIMEOUT; // Default timeout 30 seconds
        private String baseURL = Constants.BASE_URL;
        private Boolean retryOnFailure = Constants.RETRY_ON_FAILURE;// Default base url for contentstack


        /**
         * Instantiates a new Builder.
         */
        public Builder() {

        }

        public Builder setBaseUrl(String baseURL) {
            this.baseURL = baseURL;
            return this;
        }


        public Builder setProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder setRetryOnFailure(Boolean retry) {
            this.retryOnFailure = retry;
            return this;
        }

        /**
         * Set host for client instance
         *
         * @param hostname host for the Contentstack Client
         * @return Client host
         */
        public Builder setHost(String hostname) {
            this.hostname = hostname;
            return this;
        }


        /**
         * Set port for client instance
         *
         * @param port - port for the Contentstack Client
         * @return Client port
         */
        public Builder setPort(String port) {
            this.port = port;
            return this;
        }


        /**
         * Set version for client instance
         *
         * @param version version for the Contentstack Client
         * @return Client version
         */
        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }


        /**
         * Set timeout for client instance
         *
         * @param timeout timeout for the Contentstack Client
         * @return Client timeout
         */
        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }


        /**
         * Sets authtoken for the client
         *
         * @param authtoken authtoken for the client
         * @return Contentstack authtoken
         */
        public Builder setAuthtoken(String authtoken) {
            this.authtoken = authtoken;
            return this;
        }


        /**
         * Create Client instance for Contentstack
         *
         * @return Contentstack contentstack
         */
        public Contentstack build() {
            new Builder();
            String BASE_URL = Constants.PROTOCOL + "://" + this.hostname + "/" + version + "/";
            setBaseUrl(BASE_URL);
            this.instance = new Retrofit.Builder()
                    .baseUrl(this.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient(this.retryOnFailure))
                    .build();

            return new Contentstack(this);
        }


        private OkHttpClient httpClient(Boolean retryOnFailure) {
            // Add more custom fields here
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            HeaderInterceptor headerInterceptor = new HeaderInterceptor(this.authtoken);
            //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            return new OkHttpClient.Builder()
                    .addInterceptor(headerInterceptor)
                    .addInterceptor(interceptor)
                    .proxy(this.proxy)
                    .retryOnConnectionFailure(retryOnFailure)
                    .build();
        }

    }
}
