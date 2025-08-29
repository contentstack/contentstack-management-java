package com.contentstack.cms;

import java.io.IOException;
import java.net.Proxy;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import com.contentstack.cms.core.AuthInterceptor;
import com.contentstack.cms.core.Util;
import static com.contentstack.cms.core.Util.API_KEY;
import static com.contentstack.cms.core.Util.AUTHORIZATION;
import static com.contentstack.cms.core.Util.BRANCH;
import static com.contentstack.cms.core.Util.ILLEGAL_USER;
import static com.contentstack.cms.core.Util.PLEASE_LOGIN;
import com.contentstack.cms.models.Error;
import com.contentstack.cms.models.LoginDetails;
import com.contentstack.cms.models.OAuthConfig;
import com.contentstack.cms.models.OAuthTokens;
import com.contentstack.cms.oauth.OAuthHandler;
import com.contentstack.cms.oauth.OAuthInterceptor;
import com.contentstack.cms.organization.Organization;
import com.contentstack.cms.stack.Stack;
import com.contentstack.cms.user.User;
import com.google.gson.Gson;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <b>Contentstack Java Management SDK</b>
 * <br>
 * <b>Java Management SDK</b> Interact with the Content Management APIs and
 * allow you to create, update,
 * delete, and fetch content from your Contentstack account. (They are
 * read-write in nature.)
 * <br>
 * You can use them to build your own apps and manage your content from
 * Contentstack.
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
    protected OAuthInterceptor oauthInterceptor;
    protected OAuthHandler oauthHandler;
    protected String[] earlyAccess;
    protected User user;

    /**
     * All accounts registered with Contentstack are known as Users. A stack can
     * have many users with varying
     * permissions and roles
     * <p>
     * To perform User operations first get User instance like below.
     * <p>
     * <b>Example:</b>
     *
     * <pre>
     * Contentstack contentstack = new Contentstack.Builder().setAuthtoken("authtoken").build();
     * User userInstance = contentstack.user();
     * </pre>
     *
     * <br>
     * <b>OR: </b>
     *
     * <pre>
     * Contentstack contentstack = new Contentstack.Builder().setAuthtoken("authtoken").build();
     * User user = contentstack.user();
     * </pre>
     *
     * <br>
     *
     * @return User
     * @author ***REMOVED***
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#users">User
     * </a>
     * @since 2022-05-19
     */
    public User user() {
        if (!isOAuthConfigured() && this.authtoken == null) {
            throw new IllegalStateException("Please login or configure OAuth to access user");
        }
        user = new User(this.instance);
        return user;
    }

    /**
     * <b>[Note]:</b> Before executing any calls, retrieve the authtoken by
     * authenticating yourself via the Log in call of User Session. The authtoken is
     * returned to the 'Response' body of
     * the Log in call and is mandatory in all the calls.
     * <p>
     * <b>Example:</b>
     * <p>
     * All accounts registered with Contentstack are known as Users. A stack can
     * have many users with varying
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
     * Contentstack contentstack = new Contentstack.Builder().build();
     * Response login = contentstack.login("emailId", "password");
     * </pre>
     *
     * <br>
     *
     * @param emailId  the email id of the user
     * @param password the password of the user
     * @return LoginDetails
     * @throws IOException the IOException
     * @author ***REMOVED***
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#users">User
     * </a>
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
     * authenticating yourself via the Log in call of User Session. The authtoken is
     * returned to the 'Response' body of
     * the Log in call and is mandatory in all the calls.
     * <p>
     * <b>Example:</b>
     * <p>
     * All accounts registered with Contentstack are known as Users. A stack can
     * have many users with varying
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
     * Contentstack contentstack = new Contentstack.Builder().build();
     * Response login = contentstack.login("emailId", "password");
     * </pre>
     *
     * <br>
     *
     * @param emailId  the email id
     * @param password the password
     * @param tfaToken the tfa token
     * @return LoginDetails
     * @throws IOException the io exception
     * @throws IOException the IOException
     * @author ***REMOVED***
     * @see <a
     * href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#log-in-to-your-account">Login
     * your account
     * </a>
     */
    public Response<LoginDetails> login(String emailId, String password, String tfaToken) throws IOException {
        if (this.authtoken != null)
            throw new IllegalStateException(Util.USER_ALREADY_LOGGED_IN);
        user = new User(this.instance);
        Response<LoginDetails> response = user.login(emailId, password, tfaToken).execute();
        setupLoginCredentials(response);
        user = new User(this.instance);
        return response;
    }

    private void setupLoginCredentials(Response<LoginDetails> loginResponse) throws IOException {
        if (loginResponse.isSuccessful()) {
            LoginDetails loginDetails = loginResponse.body();
            if (loginDetails != null && loginDetails.getUser() != null) {
                this.authtoken = loginDetails.getUser().getAuthtoken();
                if (this.interceptor != null) {
                    this.interceptor.setAuthtoken(this.authtoken);
                }
            }
        } else {
            ResponseBody errorBody = loginResponse.errorBody();
            if (errorBody != null) {
                String errorJsonString = errorBody.string();
                logger.info(errorJsonString);
                new Gson().fromJson(errorJsonString, Error.class);
            }
        }
    }

    /**
     * The Log out of your account call is used to sign out the user of Contentstack
     * account
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack contentstack = new Contentstack.Builder().build();
     * User user = contentstack.logout();
     * </pre>
     * <p>
     *
     * @return the response
     * @throws IOException the io exception
     */
    Response<ResponseBody> logout() throws IOException {
        user = new User(this.instance);
        return user.logoutWithAuthtoken(this.authtoken).execute();
    }

    /**
     * The Log out of your account using authtoken is used to sign out the user of
     * Contentstack account
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack contentstack = new Contentstack.Builder().build();
     * User userInstance = contentstack.logoutWithAuthtoken("authtoken");
     * </pre>
     * <p>
     *
     * @param authtoken the authtoken
     * @return the response
     * @throws IOException the io exception
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
     * Organization is the top-level entity in the hierarchy of Contentstack,
     * consisting of stacks and stack resources,
     * and users. Organization allows easy management of projects as well as users
     * within the Organization.
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack contentstack = new Contentstack.Builder().build();
     * Organization org = contentstack.organization();
     * </pre>
     *
     * @return the organization
     */
    public Organization organization() {
        if (!isOAuthConfigured() && this.authtoken == null) {
            throw new IllegalStateException("Please login or configure OAuth to access organization");
        }
        return new Organization(this.instance);
    }

    /**
     * Organization is the top-level entity in the hierarchy of Contentstack,
     * consisting of stacks and stack resources,
     * and users. Organization allows easy management of projects as well as users
     * within the Organization.
     * <p>
     * <b> Example </b>
     *
     * @param organizationUid The UID of the organization that you want to retrieve
     * @return the organization
     * <br>
     * <b>Example</b>
     *
     * <pre>
     *         Contentstack contentstack = new Contentstack.Builder().build();
     *         <br>
     *         Organization org = contentstack.organization();
     *         </pre>
     */
    public Organization organization(@NotNull String organizationUid) {
        if (!isOAuthConfigured() && this.authtoken == null) {
            throw new IllegalStateException("Please login or configure OAuth to access organization");
        }
        if (organizationUid.isEmpty()) {
            throw new IllegalStateException("organizationUid can not be empty");
        }
        return new Organization(this.instance, organizationUid);
    }

    /**
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a>
     * A stack is
     * a space that stores the content of a project (a web or mobile property).
     * Within a stack, you can create content
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
        if (!isOAuthConfigured() && this.authtoken == null) {
            throw new IllegalStateException("Please login or configure OAuth to access stack");
        }
        return new Stack(this.instance);
    }

    /**
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a>
     * A stack is
     * a space that stores the content of a project (a web or mobile property).
     * Within a stack, you can create content
     * structures, content entries, users, etc. related to the project
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Stack org = client.stack();
     * </pre>
     *
     * @param header the headers for the stack
     * @return the stack instance
     */
    public Stack stack(@NotNull Map<String, Object> header) {
        if (!isOAuthConfigured() && this.authtoken == null && !header.containsKey(AUTHORIZATION)) {
            throw new IllegalStateException("Please login or configure OAuth to access stack");
        }
        return new Stack(this.instance, header);
    }

    /**
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a>
     * A stack is
     * a space that stores the content of a project (a web or mobile property).
     * Within a stack, you can create content
     * structures, content entries, users, etc. related to the project
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Stack org = client.stack();
     * </pre>
     *
     * @param managementToken the authorization for the stack
     * @param apiKey          the apiKey for the stack
     * @return the stack instance
     */
    public Stack stack(@NotNull String apiKey, @NotNull String managementToken) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(API_KEY, apiKey);
        headers.put(AUTHORIZATION, managementToken);
        return new Stack(this.instance, headers);
    }

    /**
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a>
     * A stack is
     * a space that stores the content of a project (a web or mobile property).
     * Within a stack, you can create content
     * structures, content entries, users, etc. related to the project
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Stack org = client.stack();
     * </pre>
     *
     * @param key You can provide apiKey of the stack or branchKey
     * @return the stack instance
     */
    public Stack stack(@NotNull String key) {
        HashMap<String, Object> headers = new HashMap<>();
        if (key.startsWith("blt")) {
            // When API_Key is available
            headers.put(API_KEY, key);
        } else {
            // When branch is available
            headers.put(BRANCH, key);
        }
        return new Stack(this.instance, headers);
    }

    /**
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#stacks">stack</a>
     * A stack is
     * a space that stores the content of a project (a web or mobile property).
     * Within a stack, you can create content
     * structures, content entries, users, etc. related to the project
     * <p>
     * <b> Example </b>
     *
     * <pre>
     * Contentstack client = new Contentstack.Builder().build();
     * Stack org = client.stack();
     * </pre>
     *
     * @param managementToken the authorization for the stack
     * @param apiKey          the apiKey for the stack
     * @param branch          the branch that include branching in the response
     * @return the stack instance
     */
    public Stack stack(@NotNull String apiKey, @NotNull String managementToken, @NotNull String branch) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(API_KEY, apiKey);
        headers.put(AUTHORIZATION, managementToken);
        headers.put(BRANCH, branch);
        return new Stack(this.instance, headers);
    }

    /**
     * Get the OAuth authorization URL for the user to visit
     * @return Authorization URL string
     * @throws IllegalStateException if OAuth is not configured
     */
    public String getOAuthAuthorizationUrl() {
        if (!isOAuthConfigured()) {
            throw new IllegalStateException("OAuth is not configured. Use Builder.setOAuth() or Builder.setOAuthWithPKCE()");
        }
        return oauthHandler.authorize();
    }

    /**
     * Exchange OAuth authorization code for tokens
     * @param code Authorization code from OAuth callback
     * @return CompletableFuture containing OAuth tokens
     * @throws IllegalStateException if OAuth is not configured
     */
    public CompletableFuture<OAuthTokens> exchangeOAuthCode(String code) {
        if (!isOAuthConfigured()) {
            throw new IllegalStateException("OAuth is not configured. Use Builder.setOAuth() or Builder.setOAuthWithPKCE()");
        }
        return oauthHandler.exchangeCodeForToken(code);
    }

    /**
     * Refresh the OAuth access token
     * @return CompletableFuture containing new OAuth tokens
     * @throws IllegalStateException if OAuth is not configured or no refresh token available
     */
    public CompletableFuture<OAuthTokens> refreshOAuthToken() {
        if (!isOAuthConfigured()) {
            throw new IllegalStateException("OAuth is not configured. Use Builder.setOAuth() or Builder.setOAuthWithPKCE()");
        }
        return oauthHandler.refreshAccessToken();
    }

    /**
     * Get the current OAuth tokens
     * @return Current OAuth tokens or null if not available
     */
    public OAuthTokens getOAuthTokens() {
        return oauthHandler != null ? oauthHandler.getTokens() : null;
    }

    /**
     * Check if we have valid OAuth tokens
     * @return true if we have valid tokens
     */
    public boolean hasValidOAuthTokens() {
        return oauthInterceptor != null && oauthInterceptor.hasValidTokens();
    }

    /**
     * Check if OAuth is configured
     * @return true if OAuth is configured
     */
    public boolean isOAuthConfigured() {
        return oauthInterceptor != null && oauthInterceptor.isOAuthConfigured();
    }

    /**
     * Get the OAuth handler instance
     * @return OAuth handler or null if not configured
     */
    public OAuthHandler getOAuthHandler() {
        return oauthHandler;
    }

    /**
     * Logout from OAuth session and optionally revoke authorization
     * @param revokeAuthorization If true, revokes the OAuth authorization
     * @return CompletableFuture that completes when logout is done
     */
    public CompletableFuture<Void> oauthLogout(boolean revokeAuthorization) {
        if (!isOAuthConfigured()) {
            throw new IllegalStateException("OAuth is not configured. Use Builder.setOAuth() or Builder.setOAuthWithPKCE()");
        }
        return oauthHandler.logout(revokeAuthorization);
    }

    /**
     * Logout from OAuth session without revoking authorization
     * @return CompletableFuture that completes when logout is done
     */
    public CompletableFuture<Void> oauthLogout() {
        return oauthLogout(false);
    }

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
        this.oauthInterceptor = builder.oauthInterceptor;
        this.oauthHandler = builder.oauthHandler;
        this.earlyAccess = builder.earlyAccess;
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
        private OAuthInterceptor oauthInterceptor;
        private OAuthConfig oauthConfig;
        private OAuthHandler oauthHandler;

        private String authtoken; // authtoken for client
        private String[] earlyAccess;
        private Retrofit instance; // client instance
        private String hostname = Util.HOST; // Default Host for Contentstack API (default: api.contentstack.io)
        private String port = Util.PORT; // Default PORT for Contentstack API
        private String version = Util.VERSION; // Default Version for Contentstack API
        private int timeout = Util.TIMEOUT; // Default timeout 30 seconds
        private Boolean retry = Util.RETRY_ON_FAILURE;// Default base url for contentstack

        /**
         * Default ConnectionPool holds up to 5 idle connections which
         * will be evicted after 5 minutes of inactivity.
         */
        private ConnectionPool connectionPool = new ConnectionPool(); // Connection

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
            // this blank builder constructor
        }

        /**
         * Sets proxy. (Setting proxy to the OkHttpClient) Proxy = new
         * Proxy(Proxy.Type.HTTP, new
         * InetSocketAddress(proxyHost, proxyPort));
         * <br>
         * <p>
         * {@code
         *
         * <p>
         * Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("hostname", 433));
         * Contentstack contentstack = new Contentstack.Builder().setProxy(proxy).build();
         *
         * <p>
         * }
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
         * @param retry if retry is true
         * @return the retry on failure
         */
        public Builder setRetry(@NotNull Boolean retry) {
            this.retry = retry;
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
         * @param version for the Contentstack Client
         * @return Client version
         */
        public Builder setVersion(@NotNull String version) {
            this.version = version;
            return this;
        }

        /**
         * Set timeout for client instance
         *
         * @param timeout for the Contentstack Client
         * @return Client timeout
         */
        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * Create a new connection pool with tuning parameters appropriate for a
         * single-user application.
         * The tuning parameters in this pool are subject to change in future OkHttp
         * releases. Currently,
         * this pool holds up to 5 idle connections which will be evicted after 5
         * minutes of inactivity.
         * <p>
         * <p>
         * public ConnectionPool() {
         * this(5, 5, TimeUnit.MINUTES);
         * }
         *
         * @param maxIdleConnections Maximum number of idle connections
         * @param keepAliveDuration  The Keep Alive Duration
         * @param timeUnit           A TimeUnit represents time durations at a given
         *                           unit of granularity and provides utility methods to
         *                           convert across units
         * @return instance of Builder
         * <p>
         * Example:
         * {@code
         * Contentstack cs = new Contentstack.Builder()
         * .setAuthtoken(AUTHTOKEN)
         * .setConnectionPool(5, 400, TimeUnit.MILLISECONDS)
         * .setHost("host")
         * .build();
         * Connection}
         */
        public Builder setConnectionPool(int maxIdleConnections, int keepAliveDuration, TimeUnit timeUnit) {
            this.connectionPool = new ConnectionPool(maxIdleConnections, keepAliveDuration, timeUnit);
            return this;
        }

        /**
         * Sets authtoken for the client
         *
         * @param authtoken for the client
         * @return Contentstack authtoken
         */
        public Builder setAuthtoken(String authtoken) {
            this.authtoken = authtoken;
            return this;
        }

        /**
         * Sets OAuth configuration for the client
         * @param config OAuth configuration
         * @return Builder instance
         */
        public Builder setOAuthConfig(OAuthConfig config) {
            this.oauthConfig = config;
            return this;
        }

        /**
         * Configures OAuth with client credentials (traditional flow)
         * @param appId Application ID
         * @param clientId Client ID
         * @param clientSecret Client secret
         * @param redirectUri Redirect URI
         * @return Builder instance
         */
        public Builder setOAuth(String appId, String clientId, String clientSecret, String redirectUri) {
            this.oauthConfig = OAuthConfig.builder()
                .appId(appId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build();
            return this;
        }

        /**
         * Configures OAuth with PKCE (no client secret)
         * @param appId Application ID
         * @param clientId Client ID
         * @param redirectUri Redirect URI
         * @return Builder instance
         */
        public Builder setOAuthWithPKCE(String appId, String clientId, String redirectUri) {
            this.oauthConfig = OAuthConfig.builder()
                .appId(appId)
                .clientId(clientId)
                .redirectUri(redirectUri)
                .build();
            return this;
        }

        public Builder earlyAccess(String[] earlyAccess) {
            this.earlyAccess = earlyAccess;
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
            this.instance = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient(contentstack, this.retry)).build();
            contentstack.instance = this.instance;
            
            // Initialize OAuth if configured
            if (this.oauthConfig != null) {
                this.oauthHandler = contentstack.oauthHandler = new OAuthHandler(httpClient(contentstack, this.retry), this.oauthConfig);
                this.oauthInterceptor = contentstack.oauthInterceptor = new OAuthInterceptor(this.oauthHandler);
                if (this.earlyAccess != null) {
                    this.oauthInterceptor.setEarlyAccess(this.earlyAccess);
                }
            }
        }

        private OkHttpClient httpClient(Contentstack contentstack, Boolean retryOnFailure) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectionPool(this.connectionPool)
                    .addInterceptor(logger())
                    .proxy(this.proxy)
                    .connectTimeout(Duration.ofSeconds(this.timeout))
                    .retryOnConnectionFailure(retryOnFailure);

            // Add either OAuth or traditional auth interceptor
            if (this.oauthConfig != null) {
                if (this.oauthInterceptor == null) {
                    this.oauthHandler = new OAuthHandler(builder.build(), this.oauthConfig);
                    this.oauthInterceptor = new OAuthInterceptor(this.oauthHandler);
                    if (this.earlyAccess != null) {
                        this.oauthInterceptor.setEarlyAccess(this.earlyAccess);
                    }
                }
                builder.addInterceptor(this.oauthInterceptor);
            } else {
                this.authInterceptor = contentstack.interceptor = new AuthInterceptor();
                builder.addInterceptor(this.authInterceptor);
            }

            return builder.build();
        }

        private HttpLoggingInterceptor logger() {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        }

    }
}
