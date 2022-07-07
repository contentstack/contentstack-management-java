package com.contentstack.cms;

import com.contentstack.cms.core.AuthInterceptor;
import com.contentstack.cms.models.Error;
import com.contentstack.cms.core.Util;
import com.contentstack.cms.models.LoginDetails;
import com.contentstack.cms.organization.Organization;
import com.contentstack.cms.stack.Stack;
import com.contentstack.cms.user.User;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.contentstack.cms.core.Util.*;

/**
 * The type Contentstack.
 */
public class Contentstack {

    public static final Logger logger = Logger.getLogger(Contentstack.class.getName());
    protected final String host;
    protected final String port;
    protected final String version;
    protected final int timeout;
    protected String authtoken;
    protected Retrofit instance;
    protected final Boolean retryOnFailure;
    protected final Proxy proxy;
    protected AuthInterceptor interceptor;
    protected User user;

    /**
     * All accounts registered with Contentstack are known as Users. A stack can have many users with varying
     * permissions and roles
     * <p>
     * To perform User operations first get User instance like below.
     * <p>
     * <b>Example:</b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().setAuthtoken("authtoken").build();
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
     * <br>
     *
     * @return User user
     */
    public User user() {
        if (this.authtoken == null)
            throw new NullPointerException(Util.LOGIN_FLAG);
        user = new User(this.instance);
        return user;
    }

    /**
     * <b>[Note]:</b> Before executing any calls, retrieve the authtoken by
     * authenticating yourself via the Log in call of User Session. The authtoken is returned to the 'Response' body of
     * the Log in call and is mandatory in all the calls.
     * <p>
     * <b>Example:</b>
     * <p>
     * All accounts registered with Contentstack are known as Users. A stack can have many users with varying
     * permissions and roles
     * <p>
     * To perform User operations first get User instance like below.
     * <p>
     * <b>Example:</b>
     *
     * <pre>
     * Contentstack contentstack = new Contentstack.Builder().setAuthtoken("authtoken").build();
     * Response login = contentstack.login();
     *
     * Access more other user functions from the userInstance
     * </pre>
     *
     * <br>
     * <b>OR</b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Response login = client.login("emailId", "password");
     * </pre>
     * <br>
     *
     * @param emailId
     *         the email id
     * @param password
     *         the password
     * @return response the Response type of @{@link LoginDetails}
     * @throws IOException
     *         the io exception
     */
    public Response<LoginDetails> login(String emailId, String password) throws IOException {
        if (this.authtoken != null)
            throw new IllegalStateException(Util.USER_ALREADY_LOGGED_IN);
        user = new User(this.instance);
        Response<LoginDetails> response = user.login(emailId, password).execute();
        setupLoginCredentials(response);
        return response;
    }

    /**
     * <b>[Note]:</b> Before executing any calls, retrieve the authtoken by
     * authenticating yourself via the Log in call of User Session. The authtoken is returned to the 'Response' body of
     * the Log in call and is mandatory in all the calls.
     * <p>
     * <b>Example:</b>
     * <p>
     * All accounts registered with Contentstack are known as Users. A stack can have many users with varying
     * permissions and roles
     * <p>
     * To perform User operations first get User instance like below.
     * <p>
     * <b>Example:</b>
     *
     * <pre>
     * Contentstack contentstack = new Contentstack.Builder().setAuthtoken("authtoken").build();
     * Response login = contentstack.login();
     *
     * Access more other user functions from the userInstance
     * </pre>
     *
     * <br>
     * <b>OR: </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Response login = client.login("emailId", "password");
     * </pre>
     * <br>
     *
     * @param emailId
     *         the email id
     * @param password
     *         the password
     * @param tfaToken
     *         the tfa token
     * @return response the Response type of @{@link LoginDetails} throws {@link IOException}
     * @throws IOException
     *         the io exception
     */
    public Response<LoginDetails> login(String emailId, String password, String tfaToken) throws IOException {
        if (this.authtoken != null)
            throw new IllegalStateException(Util.USER_ALREADY_LOGGED_IN);
        user = new User(this.instance);
        Response<LoginDetails> response = user.login(emailId, password, tfaToken).execute();
        setupLoginCredentials(response);
        return response;
    }

    private void setupLoginCredentials(Response<LoginDetails> loginResponse) throws IOException {
        if (loginResponse.isSuccessful()) {
            assert loginResponse.body() != null;
            logger.info(loginResponse.body().getNotice());
            this.authtoken = loginResponse.body().getUser().getAuthtoken();
            this.interceptor.setAuthtoken(this.authtoken);
        } else {
            logger.info("logging failed");
            assert loginResponse.errorBody() != null;
            String errorJsonString = loginResponse.errorBody().string();
            new Gson().fromJson(errorJsonString, Error.class);
        }
    }

    /**
     * The Log out of your account call is used to sign out the user of Contentstack account
     *
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * User userInstance = client.logout();
     * </pre>
     * <p>
     *
     * @return the response
     * @throws IOException
     *         the io exception
     */
    Response<ResponseBody> logout() throws IOException {
        user = new User(this.instance);
        return user.logoutWithAuthtoken(this.authtoken).execute();
    }

    /**
     * The Log out of your account using authtoken is used to sign out the user of Contentstack account
     *
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * User userInstance = client.logoutWithAuthtoken("authtoken");
     * </pre>
     * <p>
     *
     * @param authtoken
     *         the authtoken
     * @return the response
     * @throws IOException
     *         the io exception
     */
    Response<ResponseBody> logoutWithAuthtoken(String authtoken) throws IOException {
        user = new User(this.instance);
        this.authtoken = authtoken;
        if (authtoken != null) {
            return user.logoutWithAuthtoken(authtoken).execute();
        }
        return logout();
    }

    /**
     * Organization is the top-level entity in the hierarchy of Contentstack, consisting of stacks and stack resources,
     * and users. Organization allows easy management of projects as well as users within the Organization.
     *
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Organization org = client.organization();
     * </pre>
     *
     * @return the organization
     */
    public Organization organization() {
        if (this.authtoken == null)
            throw new IllegalStateException("Please Login to access user instance");
        return new Organization(this.instance, this.authtoken);
    }

    /**
     * <a href= "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a> A stack is
     * a space that stores the content of a project (a web or mobile property). Within a stack, you can create content
     * structures, content entries, users, etc. related to the project
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Stack org = client.stack();
     * </pre>
     *
     * @return the stack instance
     */
    public Stack stack() {
        if (this.authtoken == null)
            throw new IllegalStateException(ILLEGAL_USER);
        return new Stack(this.instance, this.authtoken);
    }


    /**
     * <a href= "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a> A stack is
     * a space that stores the content of a project (a web or mobile property). Within a stack, you can create content
     * structures, content entries, users, etc. related to the project
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * ```
     * Contentstack client = new Contentstack.Builder().build();
     * Stack org = client.stack();
     * ```
     * </pre>
     *
     * @param header
     *         the headers for the stack
     * @return the stack instance
     */
    public Stack stack(@NotNull Map<String, Object> header) {
        if (this.authtoken == null)
            if (header.size() == 0) {
                throw new IllegalStateException(PLEASE_LOGIN);
            }
        return new Stack(this.instance, header);
    }


    /**
     * <a href= "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a> A stack is
     * a space that stores the content of a project (a web or mobile property). Within a stack, you can create content
     * structures, content entries, users, etc. related to the project
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Stack org = client.stack();
     * </pre>
     *
     * @param managementToken
     *         the authorization for the stack
     * @param apiKey
     *         the apiKey for the stack
     * @return the stack instance
     */
    public Stack stack(@NotNull String apiKey, @NotNull String managementToken) {
        if (this.authtoken == null)
            throw new IllegalStateException(PLEASE_LOGIN);
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(API_KEY, apiKey);
        headers.put(AUTHORIZATION, managementToken);
        return new Stack(this.instance, headers);
    }

    /**
     * <a href= "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a> A stack is
     * a space that stores the content of a project (a web or mobile property). Within a stack, you can create content
     * structures, content entries, users, etc. related to the project
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Stack org = client.stack();
     * </pre>
     *
     * @param managementToken
     *         the authorization for the stack
     * @param apiKey
     *         the apiKey for the stack
     * @param branch
     *         the branch that include branching in the response
     * @return the stack instance
     */
    public Stack stack(@NotNull String apiKey, @NotNull String managementToken, @NotNull String branch) {
        if (this.authtoken == null)
            throw new IllegalStateException(PLEASE_LOGIN);
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(API_KEY, apiKey);
        headers.put(AUTHORIZATION, managementToken);
        headers.put(BRANCH, branch);
        return new Stack(this.instance, headers);
    }


    /**
     * Instantiates a new Contentstack.
     *
     * @param builder
     *         the builder
     */
    public Contentstack(Builder builder) {
        this.host = builder.hostname;
        this.port = builder.port;
        this.version = builder.version;
        this.timeout = builder.timeout;
        this.authtoken = builder.authtoken;
        this.instance = builder.instance;
        this.retryOnFailure = builder.retry;
        this.proxy = builder.proxy;
        this.interceptor = builder.authInterceptor;
    }

    /**
     * The type Builder.
     */
    public static class Builder {

        /**
         * The Proxy.
         */
        protected Proxy proxy;
        private AuthInterceptor authInterceptor;
        private String authtoken; // authtoken for client
        private Retrofit instance; // client instance
        private String hostname = Util.HOST; // Default Host for Contentstack API (default: api.contentstack.io)
        private String port = Util.PORT; // Default PORT for Contentstack API
        private String version = Util.VERSION; // Default Version for Contentstack API
        private int timeout = Util.TIMEOUT; // Default timeout 30 seconds
        private Boolean retry = Util.RETRY_ON_FAILURE;// Default base url for contentstack

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
        }

        /**
         * Sets proxy. (Setting proxy to the OkHttpClient) Proxy proxy = new Proxy(Proxy.Type.HTTP, new
         * InetSocketAddress(proxyHost, proxyPort));
         * <br>
         * <pre>
         *         Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("hostname", 433));
         *         Contentstack contentstack = new Contentstack.Builder().setProxy(proxy).build();
         *     </pre>
         *
         * @param proxy
         *         the proxy
         * @return the Builder instance
         */
        public Builder setProxy(@NotNull Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        /**
         * Sets retry on failure.
         *
         * @param retry
         *         if retry is true
         * @return the retry on failure
         */
        public Builder setRetry(@NotNull Boolean retry) {
            this.retry = retry;
            return this;
        }

        /**
         * Set host for client instance
         *
         * @param hostname
         *         host for the Contentstack Client
         * @return Client host
         */
        public Builder setHost(@NotNull String hostname) {
            this.hostname = hostname;
            return this;
        }

        /**
         * Set port for client instance
         *
         * @param port
         *         - port for the Contentstack Client
         * @return Client port
         */
        public Builder setPort(@NotNull String port) {
            this.port = port;
            return this;
        }

        /**
         * Set version for client instance
         *
         * @param version
         *         for the Contentstack Client
         * @return Client version
         */
        public Builder setVersion(@NotNull String version) {
            this.version = version;
            return this;
        }

        /**
         * Set timeout for client instance
         *
         * @param timeout
         *         for the Contentstack Client
         * @return Client timeout
         */
        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * Sets authtoken for the client
         *
         * @param authtoken
         *         for the client
         * @return Contentstack authtoken
         */
        public Builder setAuthtoken(String authtoken) {
            this.authtoken = authtoken;
            return this;
        }

        /**
         * Build contentstack.
         *
         * @return the contentstack
         */
        public Contentstack build() {
            Contentstack contentstack = new Contentstack(this);
            validateClient(contentstack);
            return contentstack;
        }

        private void validateClient(Contentstack contentstack) {
            String baseUrl = Util.PROTOCOL + "://" + this.hostname + "/" + version + "/";
            this.instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient(contentstack, this.retry)).build();
            contentstack.instance = this.instance;
        }

        private OkHttpClient httpClient(Contentstack contentstack, Boolean retryOnFailure) {
            this.authInterceptor = contentstack.interceptor = new AuthInterceptor();
            return new OkHttpClient.Builder()
                    .addInterceptor(this.authInterceptor)
                    .addInterceptor(logger())
                    .proxy(this.proxy)
                    .retryOnConnectionFailure(retryOnFailure)
                    .build();
        }

        private HttpLoggingInterceptor logger() {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        }

    }
}
