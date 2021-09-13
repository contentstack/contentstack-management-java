package com.contentstack.cms;

import com.contentstack.cms.core.Constants;
import com.contentstack.cms.core.Error;
import com.contentstack.cms.core.HeaderInterceptor;
import com.contentstack.cms.organization.Organization;
import com.contentstack.cms.user.CSResponse;
import com.contentstack.cms.user.LoginDetails;
import com.contentstack.cms.user.User;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.Proxy;

import static com.contentstack.cms.core.Constants.LOGIN_FLAG;
import static com.contentstack.cms.core.Constants.USER_ALREADY_LOGGED_IN;

public class Contentstack {

    protected String host;
    protected String port;
    protected String version;
    protected int timeout;
    protected String authtoken;
    protected Retrofit instance;
    protected Boolean retryOnFailure;
    protected Proxy proxy;
    protected HeaderInterceptor headerInterceptor;
    User user;

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
        this.headerInterceptor = builder.headerInterceptor;
        user = new User(this.instance);
    }

    /**
     * All accounts registered with Contentstack are known as Users. A stack can
     * have many users with varying permissions and roles
     * <p>
     * To perform User operations first get User instance like below.
     * <p>
     * <b>Example:</b>
     *
     * <pre>
     * Client client = new Client.Builder().setAuthtoken("authtoken").build();
     * User userInstance = client.user();
     * </pre>
     *
     * <br>
     * <b>OR: </b>
     *
     * <pre>
     * Client client = new Client.Builder().build();
     * User userInstance = client.user();
     * </pre>
     * <p>
     *
     * @return User user
     */
    public User user() {
        if (this.authtoken == null)
            throw new NullPointerException(LOGIN_FLAG);
        return new User(this.instance);
    }

    /**
     * <b>[Note]:</b> Before executing any calls, retrieve the authtoken by
     * authenticating yourself via the Log in call of User Session. The authtoken is
     * returned in the 'Response' body of the Log in call and is mandatory in all of
     * the calls.
     * <p>
     * <b>Example:</b>
     * <p>
     * All accounts registered with Contentstack are known as Users. A stack can
     * have many users with varying permissions and roles
     * <p>
     * To perform User operations first get User instance like below.
     * <p>
     * <b>Example:</b>
     *
     * <pre>
     *  Client client = new Client.Builder().setAuthtoken("authtoken").build();
     *  User userInstance = client.user();
     *
     * Access more other user functions from the userInstance
     * </pre>
     *
     * <br>
     * <b>OR: </b>
     *
     * <pre>
     * Client client = new Client.Builder().build();
     * User userInstance = client.login("emailId", "password");
     * </pre>
     * <p>
     *
     * @param emailId  the email id
     * @param password the password
     * @return response the @Response<ResponseBody>
     */

    public Response<ResponseBody> login(String emailId, String password) throws IOException {
        if (this.authtoken != null)
            throw new IllegalStateException(USER_ALREADY_LOGGED_IN);
        user = new User(this.instance);
        Response<ResponseBody> response = user.login(emailId, password).execute();
        setupLoginCredentials(response);
        return response;
    }

    /* Setting up authtoken to the header interceptor */
    private void setupLoginCredentials(Response<ResponseBody> responseBody) throws IOException {
        if (responseBody.isSuccessful()) {
            LoginDetails loginDetails = new CSResponse(responseBody).toModel(LoginDetails.class);
            this.authtoken = loginDetails.getUser()
                    .getAuthtoken();
            this.headerInterceptor.setAuthtoken(this.authtoken);
        } else {
            new CSResponse(responseBody).toModel(Error.class);
        }
    }

    Response<ResponseBody> logout() throws IOException {
        user = new User(this.instance);
        return user.logoutWithAuthtoken(this.authtoken).execute();
    }

    Response<ResponseBody> logoutWithAuthtoken(String authtoken) throws IOException {
        user = new User(this.instance);
        if (authtoken != null) {
            return user.logoutWithAuthtoken(authtoken).execute();
        }
        return logout();
    }

    public Organization organization() {
        if (this.authtoken == null)
            throw new NullPointerException("Please LogIn to access user instance");
        return new Organization(this.instance);
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        public Proxy proxy;
        private HeaderInterceptor headerInterceptor;
        private String authtoken; // authtoken for client
        private Retrofit instance; // client instance
        private String hostname = Constants.HOST; // Default Host for Contentstack API (default: api.contentstack.io)
        private String port = Constants.PORT; // Default PORT for Contentstack API
        private String version = Constants.VERSION; // Default Version for Contentstack API
        private int timeout = Constants.TIMEOUT; // Default timeout 30 seconds
        private Boolean retryOnFailure = Constants.RETRY_ON_FAILURE;// Default base url for contentstack

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
            // default builder constroctor
        }

        /**
         * Sets proxy. (Setting proxy to the OkHttpClient) Proxy proxy = new
         * Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
         * <p>
         * Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("hostname",
         * 433)); Contentstack contentstack = new
         * Contentstack.Builder().setProxy(proxy).build();
         *
         * @param proxy the proxy
         * @return the Builder instance
         */
        public Builder setProxy(@NotNull Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        /**
         * Sets retry on failure.
         *
         * @param retry the retry
         * @return the retry on failure
         */
        public Builder setRetryOnFailure(@NotNull Boolean retry) {
            this.retryOnFailure = retry;
            return this;
        }

        /**
         * Set host for client instance
         *
         * @param hostname host for the Contentstack Client
         * @return Client host
         */
        public Builder setHost(@NotNull String hostname) {
            this.hostname = hostname;
            return this;
        }

        /**
         * Set port for client instance
         *
         * @param port - port for the Contentstack Client
         * @return Client port
         */
        public Builder setPort(@NotNull String port) {
            this.port = port;
            return this;
        }

        /**
         * Set version for client instance
         *
         * @param version version for the Contentstack Client
         * @return Client version
         */
        public Builder setVersion(@NotNull String version) {
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
        public Builder setAuthtoken(@NotNull String authtoken) {
            this.authtoken = authtoken;
            return this;
        }

        protected HeaderInterceptor getHeaderInterceptor() {
            return this.headerInterceptor;
        }

        /**
         * Create Client instance for Contentstack
         *
         * @return Contentstack contentstack
         */
        public Contentstack build() {
            new Builder();
            String baseUrl = Constants.PROTOCOL + "://" + this.hostname + "/" + version + "/";
            this.instance = createClient(baseUrl);
            return new Contentstack(this);
        }

        private Retrofit createClient(@NotNull String baseUrl) {
            this.instance = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient(this.retryOnFailure)).build();
            return this.instance;
        }

        private OkHttpClient httpClient(Boolean retryOnFailure) {
            HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.NONE);
            this.headerInterceptor = new HeaderInterceptor(this.authtoken);
            return new OkHttpClient.Builder().addInterceptor(headerInterceptor).addInterceptor(loggerInterceptor)
                    .proxy(this.proxy).retryOnConnectionFailure(retryOnFailure).build();
        }

    }
}
