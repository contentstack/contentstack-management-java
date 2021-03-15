package com.contentstack.cms;
import com.contentstack.cms.core.Constants;
import com.contentstack.cms.core.HeaderInterceptor;
import com.contentstack.cms.user.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;


/**
 * Contentstack is an API-based, headless content management platform
 *
 * @author  Shailesh Mishra
 * @version 1.0.0
 * @since   2021-03-31
 */
public class Contentstack {


    public static class Client {

        protected String protocol = Constants.PROTOCOL; // Protocol for Contentstack API
        protected String hostname = Constants.HOST; // Host for Contentstack API (default: api.contentstack.io)
        protected String port = Constants.PORT; // PORT for Contentstack API
        protected String version = Constants.VERSION; // Version for Contentstack API
        protected String baseURL;
        protected int timeout = Constants.TIMEOUT; // Default timeout 30 seconds
        protected Retrofit instance; // client instance
        protected String authtoken; // authtoken for client


        /**
         * Client for the Contentstack
         * @param authtoken authtoken for the Contentstack Client
         */
        public Client(String authtoken) {
            if (authtoken.isEmpty()){
                throw new NullPointerException("authtoken can not be null or empty");
            }
            this.setAuthtoken(authtoken);
            initialiseClient();
        }

        /**
         * Initialise Client for Contentstack
         */
        public Client() {
            // Even when build method not called return client instance
            initialiseClient();
        }

        /**s
         * Results authtoken for the client
         *
         * @return String authtoken
         */
        protected String getAuthtoken() {
            return this.authtoken;
        }

        /**
         *  Set/Update authtoken for the client
         * @param authtoken authtoken for the Contentstack Client
         */
        public Client setAuthtoken(String authtoken) {
            this.authtoken = authtoken;
            return this;
        }


        private Client(Retrofit instance) {
            this.instance=instance;
        }

        /**
         * Set protocol for client instance
         * @param protocol protocol for the Contentstack Client
         * @return Client
         */
        public Client setProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        /**
         * Set host for client instance
         * @param hostname - hostname for the Contentstack Client
         * @return Client
         */
        public Client setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        /**
         * Set port for client instance
         * @param port - port for the Contentstack Client
         * @return Client
         */
        public Client setPort(String port) {
            this.port = port;
            return this;
        }

        /**
         * Set version for client instance
         * @param version - version for the Contentstack Client
         * @return Client
         */
        public Client setVersion(String version) {
            this.version = version;
            return this;
        }

        /**
         * Set timeout for client instance
         * @param timeout - timeout for the Contentstack Client
         * @return Client
         */
        public Client setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        private Client initialiseClient(){
            //TODO: check port name from javascript
            // https://stackoverflow.com/questions/42131639/retrofit-request-on-ip-address-with-port
            this.baseURL = this.protocol+"://"+this.hostname+"/"+this.version+"/";
            //if (this.port.isEmpty()){
            //this.baseURL = this.protocol+"://"+this.hostname+":"+this.port+"/"+this.version+"/";
            //}
            okHttpClient();
            Retrofit instance = client();
            return new Client(instance);
        }

        private Retrofit client(){
            this.instance = new Retrofit.Builder()
                    .baseUrl(this.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient())
                    .build();
            return this.instance;
        }

        private OkHttpClient okHttpClient(){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder()
                    .readTimeout(this.timeout, TimeUnit.SECONDS)
                    .connectTimeout(this.timeout, TimeUnit.SECONDS)
                    .pingInterval(200, TimeUnit.MILLISECONDS)
                    .addInterceptor(new HeaderInterceptor(this.authtoken))
                    .retryOnConnectionFailure(true)
                    .build();
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
         * <pre>Client clientInstance = new Contentstack.Client();</pre>
         * <br>
         * <b>OR: </b>
         * <pre>Client clientInstance = new Contentstack.Client(authtoken);</pre>
         * <p>
         *
         * @return User
         */
        public User user(){
            return new User(this.instance);
        }

    }


}
