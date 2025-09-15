package com.contentstack.cms.oauth;

import java.io.IOException;
import java.util.concurrent.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import com.contentstack.cms.models.OAuthConfig;
import com.contentstack.cms.models.OAuthTokens;
import com.google.gson.Gson;

import okhttp3.*;
import okio.Buffer;


@RunWith(MockitoJUnitRunner.class)
public class OAuthTest {

    // Using lenient mocks to avoid unnecessary stubbing warnings
    // when some mock setups are not used in every test

    private static final String TEST_APP_ID = "test_app_id";
    private static final String TEST_CLIENT_ID = "test_client_id";
    private static final String TEST_CLIENT_SECRET = "test_client_secret";
    private static final String TEST_REDIRECT_URI = "https://example.com/callback";
    private static final String TEST_AUTH_CODE = "test_auth_code";
    private static final String TEST_ACCESS_TOKEN = "test_access_token";
    private static final String TEST_REFRESH_TOKEN = "test_refresh_token";

    @Mock(lenient = true)
    private OkHttpClient mockHttpClient;

    @Mock(lenient = true)
    private Call mockCall;

    @Mock(lenient = true)
    private Response mockResponse;

    @Mock(lenient = true)
    private ResponseBody mockResponseBody;

    private OAuthHandler pkceHandler;
    private OAuthHandler clientSecretHandler;
    private Contentstack pkceClient;
    private Contentstack clientSecretClient;
    private Gson gson;

    @Before
    public void setup() {
        gson = new Gson();
        
        // Create OAuth configuration for PKCE (no client secret)
        OAuthConfig pkceConfig = OAuthConfig.builder()
                .appId(TEST_APP_ID)
                .clientId(TEST_CLIENT_ID)
                .redirectUri(TEST_REDIRECT_URI)
                .build();

        pkceHandler = new OAuthHandler(mockHttpClient, pkceConfig);
        
        // Create OAuth configuration with client secret
        OAuthConfig clientSecretConfig = OAuthConfig.builder()
                .appId(TEST_APP_ID)
                .clientId(TEST_CLIENT_ID)
                .clientSecret(TEST_CLIENT_SECRET)
                .redirectUri(TEST_REDIRECT_URI)
                .build();

        clientSecretHandler = new OAuthHandler(mockHttpClient, clientSecretConfig);
        
        // Create Contentstack clients
        pkceClient = new Contentstack.Builder()
                .setOAuth(TEST_APP_ID, TEST_CLIENT_ID, TEST_REDIRECT_URI)
                .build();
                
        clientSecretClient = new Contentstack.Builder()
                .setOAuth(TEST_APP_ID, TEST_CLIENT_ID, TEST_REDIRECT_URI, Util.HOST, TEST_CLIENT_SECRET)
                .build();
    }

    // =================
    // CONFIGURATION TESTS
    // =================

    @Test
    public void testPKCEConfiguration() {
        assertTrue("PKCE OAuth should be configured", pkceClient.isOAuthConfigured());
        assertNotNull("PKCE OAuth handler should exist", pkceClient.getOAuthHandler());
        assertFalse("Should not have tokens initially", pkceClient.hasValidOAuthTokens());
        assertTrue("Should be PKCE enabled", pkceHandler.getConfig().isPkceEnabled());
    }

    @Test
    public void testClientSecretConfiguration() {
        assertTrue("Client Secret OAuth should be configured", clientSecretClient.isOAuthConfigured());
        assertNotNull("Client Secret OAuth handler should exist", clientSecretClient.getOAuthHandler());
        assertFalse("Should not have tokens initially", clientSecretClient.hasValidOAuthTokens());
        assertFalse("Should not be PKCE enabled", clientSecretHandler.getConfig().isPkceEnabled());
    }

    @Test
    public void testInvalidConfigurations() {
        // Test invalid app ID
        try {
            new Contentstack.Builder()
                    .setOAuth("", TEST_CLIENT_ID, TEST_REDIRECT_URI)
                    .build();
            fail("Should throw exception for empty app ID");
        } catch (IllegalArgumentException e) {
            assertTrue("Should mention app ID", e.getMessage().contains("appId"));
        }

        // Test invalid client ID
        try {
            new Contentstack.Builder()
                    .setOAuth(TEST_APP_ID, "", TEST_CLIENT_SECRET, TEST_REDIRECT_URI)
                    .build();
            fail("Should throw exception for empty client ID");
        } catch (IllegalArgumentException e) {
            assertTrue("Should mention client ID", e.getMessage().contains("clientId"));
        }

        // Test invalid redirect URI
        try {
            new Contentstack.Builder()
                    .setOAuth(TEST_APP_ID, TEST_CLIENT_ID, TEST_CLIENT_SECRET, "invalid-url")
                    .build();
            fail("Should throw exception for invalid redirect URI");
        } catch (IllegalArgumentException e) {
            assertTrue("Should mention redirect URI", e.getMessage().contains("redirectUri"));
        }
    }

    // =================
    // AUTHORIZATION URL TESTS
    // =================

    @Test
    public void testPKCEAuthorizationUrlGeneration() {
        String authUrl = pkceHandler.authorize();
        
        assertNotNull("Authorization URL should not be null", authUrl);
        assertTrue("URL should contain app ID", authUrl.contains("app_id=" + TEST_APP_ID));
        assertTrue("URL should contain client ID", authUrl.contains("client_id=" + TEST_CLIENT_ID));
        assertTrue("URL should contain redirect URI", authUrl.contains("redirect_uri="));
        assertTrue("URL should contain PKCE code challenge", authUrl.contains("code_challenge="));
        assertTrue("URL should contain PKCE method", authUrl.contains("code_challenge_method=S256"));
        assertTrue("URL should contain response type", authUrl.contains("response_type=code"));
        assertFalse("URL should not contain client_secret", authUrl.contains("client_secret"));
    }

    @Test
    public void testClientSecretAuthorizationUrlGeneration() {
        String authUrl = clientSecretHandler.authorize();
        
        assertNotNull("Authorization URL should not be null", authUrl);
        assertTrue("URL should contain app ID", authUrl.contains("app_id=" + TEST_APP_ID));
        assertTrue("URL should contain client ID", authUrl.contains("client_id=" + TEST_CLIENT_ID));
        assertTrue("URL should contain redirect URI", authUrl.contains("redirect_uri="));
        assertTrue("URL should contain response type", authUrl.contains("response_type=code"));
        assertFalse("URL should not contain PKCE code challenge", authUrl.contains("code_challenge="));
        assertFalse("URL should not contain PKCE method", authUrl.contains("code_challenge_method"));
    }

    @Test
    public void testAuthUrlUniqueness() {
        // Create two PKCE handlers and verify they generate different URLs due to different code challenges
        OAuthConfig config1 = OAuthConfig.builder()
                .appId(TEST_APP_ID)
                .clientId(TEST_CLIENT_ID)
                .redirectUri(TEST_REDIRECT_URI)
                .build();
        
        OAuthConfig config2 = OAuthConfig.builder()
                .appId(TEST_APP_ID)
                .clientId(TEST_CLIENT_ID)
                .redirectUri(TEST_REDIRECT_URI)
                .build();

        OAuthHandler handler1 = new OAuthHandler(mockHttpClient, config1);
        OAuthHandler handler2 = new OAuthHandler(mockHttpClient, config2);

        String url1 = handler1.authorize();
        String url2 = handler2.authorize();

        assertNotEquals("URLs should be different due to different PKCE challenges", url1, url2);
    }

    @Test
    public void testDefaultOAuthEndpoints() {
        // Test with default hosts
        OAuthConfig config = OAuthConfig.builder()
                .appId(TEST_APP_ID)
                .clientId(TEST_CLIENT_ID)
                .redirectUri(TEST_REDIRECT_URI)
                .build();
        OAuthHandler handler = new OAuthHandler(mockHttpClient, config);

        String authUrl = handler.authorize();
        String tokenUrl = config.getTokenEndpoint();

        assertTrue("Auth URL should use default app host",
                authUrl.contains(Util.OAUTH_APP_HOST));
        assertTrue("Token URL should use default API host",
                tokenUrl.contains(Util.OAUTH_API_HOST));
    }

    @Test
    public void testHostTransformations() {
        // Test cases: {API Host, Expected App Host, Expected Token Host}
        String[][] testCases = {
            // Default region
            {"api.contentstack.io", "app.contentstack.com", "developerhub-api.contentstack.com"},
            {"api-contentstack.com", "app-contentstack.com", "developerhub-api-contentstack.com"},
            
            // AWS regions
            {"eu-api.contentstack.com", "eu-app.contentstack.com", "eu-developerhub-api.contentstack.com"},
            {"eu-api-contentstack.com", "eu-app-contentstack.com", "eu-developerhub-api-contentstack.com"},
            {"au-api.contentstack.com", "au-app.contentstack.com", "au-developerhub-api.contentstack.com"},
            {"au-api-contentstack.com", "au-app-contentstack.com", "au-developerhub-api-contentstack.com"},
            
            // Azure regions
            {"azure-na-api.contentstack.com", "azure-na-app.contentstack.com", "azure-na-developerhub-api.contentstack.com"},
            {"azure-na-api-contentstack.com", "azure-na-app-contentstack.com", "azure-na-developerhub-api-contentstack.com"},
            {"azure-eu-api.contentstack.com", "azure-eu-app.contentstack.com", "azure-eu-developerhub-api.contentstack.com"},
            {"azure-eu-api-contentstack.com", "azure-eu-app-contentstack.com", "azure-eu-developerhub-api-contentstack.com"},
            
            // GCP regions
            {"gcp-na-api.contentstack.com", "gcp-na-app.contentstack.com", "gcp-na-developerhub-api.contentstack.com"},
            {"gcp-na-api-contentstack.com", "gcp-na-app-contentstack.com", "gcp-na-developerhub-api-contentstack.com"},
            {"gcp-eu-api.contentstack.com", "gcp-eu-app.contentstack.com", "gcp-eu-developerhub-api.contentstack.com"},
            {"gcp-eu-api-contentstack.com", "gcp-eu-app-contentstack.com", "gcp-eu-developerhub-api-contentstack.com"}
        };

        for (String[] testCase : testCases) {
            String apiHost = testCase[0];
            String expectedAppHost = testCase[1];
            String expectedTokenHost = testCase[2];

            OAuthConfig config = OAuthConfig.builder()
                    .appId(TEST_APP_ID)
                    .clientId(TEST_CLIENT_ID)
                    .redirectUri(TEST_REDIRECT_URI)
                    .host(apiHost)
                    .build();
            OAuthHandler handler = new OAuthHandler(mockHttpClient, config);

            String authUrl = handler.authorize();
            String tokenUrl = config.getTokenEndpoint();

            assertTrue(String.format("Auth URL for %s should contain %s", apiHost, expectedAppHost),
                    authUrl.contains(expectedAppHost));
            assertTrue(String.format("Token URL for %s should contain %s", apiHost, expectedTokenHost),
                    tokenUrl.contains(expectedTokenHost));
        }
    }

    @Test
    public void testHostStorage() {
        String testHost = "eu-api.contentstack.com";
        
        // Test host storage in OAuthConfig
        OAuthConfig config = OAuthConfig.builder()
                .appId(TEST_APP_ID)
                .clientId(TEST_CLIENT_ID)
                .redirectUri(TEST_REDIRECT_URI)
                .host(testHost)
                .build();
                
        assertEquals("Host should be stored in OAuthConfig",
                testHost, config.getHost());
                
        // Test host storage via Contentstack.Builder
        Contentstack client = new Contentstack.Builder()
                .setOAuth(TEST_APP_ID, TEST_CLIENT_ID, TEST_REDIRECT_URI, testHost, TEST_CLIENT_SECRET)
                .build();
                
        String authUrl = client.getOAuthAuthorizationUrl();
        assertTrue("Auth URL should use stored host",
                authUrl.contains("eu-app.contentstack.com"));
                
        // Test host storage via PKCE builder
        client = new Contentstack.Builder()
                .setOAuth(TEST_APP_ID, TEST_CLIENT_ID, TEST_REDIRECT_URI, testHost)
                .build();
                
        authUrl = client.getOAuthAuthorizationUrl();
        assertTrue("Auth URL should use stored host with PKCE",
                authUrl.contains("eu-app.contentstack.com"));
    }

    @Test
    public void testDefaultHosts() {
        // Test with no host specified
        OAuthConfig config = OAuthConfig.builder()
                .appId(TEST_APP_ID)
                .clientId(TEST_CLIENT_ID)
                .redirectUri(TEST_REDIRECT_URI)
                .build();
        OAuthHandler handler = new OAuthHandler(mockHttpClient, config);

        String authUrl = handler.authorize();
        String tokenUrl = config.getTokenEndpoint();

        assertTrue("Auth URL should use default app host",
                authUrl.contains(Util.OAUTH_APP_HOST));
        assertTrue("Token URL should use default API host",
                tokenUrl.contains(Util.OAUTH_API_HOST));
    }

    @Test
    public void testCustomEndpoints() {
        // Test with custom endpoints
        String customAuthEndpoint = "https://custom.auth.endpoint";
        String customTokenEndpoint = "https://custom.token.endpoint";

        OAuthConfig config = OAuthConfig.builder()
                .appId(TEST_APP_ID)
                .clientId(TEST_CLIENT_ID)
                .redirectUri(TEST_REDIRECT_URI)
                .authEndpoint(customAuthEndpoint)
                .tokenEndpoint(customTokenEndpoint)
                .build();
        OAuthHandler handler = new OAuthHandler(mockHttpClient, config);

        String authUrl = handler.authorize();
        String tokenUrl = config.getTokenEndpoint();

        assertEquals("Should use custom auth endpoint",
                customAuthEndpoint, authUrl);
        assertEquals("Should use custom token endpoint",
                customTokenEndpoint, tokenUrl);
    }



    // =================
    // TOKEN EXCHANGE TESTS
    // =================

    @Test
    public void testSuccessfulPKCETokenExchange() throws IOException, ExecutionException, InterruptedException {
        setupSuccessfulTokenResponse();

        CompletableFuture<OAuthTokens> future = pkceHandler.exchangeCodeForToken(TEST_AUTH_CODE);
        OAuthTokens tokens = future.get();

        verifyTokensValid(tokens);
        verifyPKCETokenRequest();
    }

    @Test
    public void testSuccessfulClientSecretTokenExchange() throws IOException, ExecutionException, InterruptedException {
        setupSuccessfulTokenResponse();

        CompletableFuture<OAuthTokens> future = clientSecretHandler.exchangeCodeForToken(TEST_AUTH_CODE);
        OAuthTokens tokens = future.get();

        verifyTokensValid(tokens);
        verifyClientSecretTokenRequest();
    }

    @Test
    public void testInvalidAuthCode() {
        CompletableFuture<OAuthTokens> nullFuture = pkceHandler.exchangeCodeForToken(null);
        assertTrue("Future should be completed exceptionally for null code", nullFuture.isCompletedExceptionally());
        
        CompletableFuture<OAuthTokens> emptyFuture = pkceHandler.exchangeCodeForToken("   ");
        assertTrue("Future should be completed exceptionally for empty code", emptyFuture.isCompletedExceptionally());
    }

    // =================
    // TOKEN REFRESH TESTS
    // =================

    @Test
    public void testPKCETokenRefresh() throws IOException, ExecutionException, InterruptedException {
        // Setup initial expired tokens
        OAuthTokens expiredTokens = createExpiredTokens();
        pkceHandler.setTokens(expiredTokens);
        
        setupSuccessfulRefreshResponse("new_pkce_access", "new_pkce_refresh");

        CompletableFuture<OAuthTokens> future = pkceHandler.refreshAccessToken();
        OAuthTokens newTokens = future.get();

        verifyRefreshedTokens(newTokens, "new_pkce_access", "new_pkce_refresh");
        verifyRefreshRequest();
    }

    @Test
    public void testClientSecretTokenRefresh() throws IOException, ExecutionException, InterruptedException {
        // Setup initial expired tokens
        OAuthTokens expiredTokens = createExpiredTokens();
        clientSecretHandler.setTokens(expiredTokens);
        
        setupSuccessfulRefreshResponse("new_secret_access", "new_secret_refresh");

        CompletableFuture<OAuthTokens> future = clientSecretHandler.refreshAccessToken();
        OAuthTokens newTokens = future.get();

        verifyRefreshedTokens(newTokens, "new_secret_access", "new_secret_refresh");
        verifyRefreshRequestWithSecret();
    }

    @Test
    public void testRefreshWithoutTokens() {
        CompletableFuture<OAuthTokens> future = pkceHandler.refreshAccessToken();
        
        assertTrue("Future should complete exceptionally without tokens", future.isCompletedExceptionally());
        
        try {
            future.get();
            fail("Should have thrown exception for missing tokens");
        } catch (ExecutionException | InterruptedException e) {
            assertTrue("Should be IllegalStateException", e.getCause() instanceof IllegalStateException);
        }
    }


    // =================
    // HELPER METHODS
    // =================

    private OAuthTokens createValidTokens() {
        OAuthTokens tokens = new OAuthTokens();
        tokens.setAccessToken(TEST_ACCESS_TOKEN);
        tokens.setRefreshToken(TEST_REFRESH_TOKEN);
        tokens.setTokenType("Bearer");
        tokens.setExpiresIn(3600L); // 1 hour
        tokens.setScope("read write");
        return tokens;
    }

    private OAuthTokens createExpiredTokens() {
        OAuthTokens tokens = createValidTokens();
        tokens.setExpiresIn(1L); // Make it expire quickly
        // Wait to ensure expiration
        try {
            Thread.sleep(3500); // Wait longer than 1 second + 2 minute buffer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return tokens;
    }

    private void setupSuccessfulTokenResponse() throws IOException {
        OAuthTokens expectedTokens = createValidTokens();
        String tokenResponse = gson.toJson(expectedTokens);
        
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockResponse.headers()).thenReturn(Headers.of());
        when(mockResponse.code()).thenReturn(200);
        when(mockResponseBody.string()).thenReturn(tokenResponse);
    }

    private void setupSuccessfulRefreshResponse(String newAccessToken, String newRefreshToken) throws IOException {
        OAuthTokens refreshedTokens = createValidTokens();
        refreshedTokens.setAccessToken(newAccessToken);
        refreshedTokens.setRefreshToken(newRefreshToken);
        String refreshResponse = gson.toJson(refreshedTokens);
        
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockResponse.headers()).thenReturn(Headers.of());
        when(mockResponse.code()).thenReturn(200);
        when(mockResponseBody.string()).thenReturn(refreshResponse);
    }

    private void setupHttpErrorResponse(int statusCode, String errorMessage) throws IOException {
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockResponse.isSuccessful()).thenReturn(false);
        when(mockResponse.code()).thenReturn(statusCode);
        when(mockResponse.message()).thenReturn(errorMessage);
        when(mockResponse.headers()).thenReturn(Headers.of());
        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockResponseBody.string()).thenReturn(errorMessage);
    }

    private void setupMalformedJsonResponse() throws IOException {
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockResponse.headers()).thenReturn(Headers.of());
        when(mockResponse.code()).thenReturn(200);
        when(mockResponseBody.string()).thenReturn("invalid json");
    }

    private void verifyTokensValid(OAuthTokens tokens) {
        assertNotNull("Tokens should not be null", tokens);
        assertEquals("Access token should match", TEST_ACCESS_TOKEN, tokens.getAccessToken());
        assertEquals("Refresh token should match", TEST_REFRESH_TOKEN, tokens.getRefreshToken());
        assertEquals("Token type should be Bearer", "Bearer", tokens.getTokenType());
        assertTrue("Tokens should be valid", tokens.isValid());
    }

    private void verifyRefreshedTokens(OAuthTokens newTokens, String expectedAccessToken, String expectedRefreshToken) {
        assertNotNull("New tokens should not be null", newTokens);
        assertEquals("New access token should match", expectedAccessToken, newTokens.getAccessToken());
        assertEquals("New refresh token should match", expectedRefreshToken, newTokens.getRefreshToken());
        assertNotEquals("Access token should be different", TEST_ACCESS_TOKEN, newTokens.getAccessToken());
    }

    private void verifyPKCETokenRequest() {
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        verify(mockHttpClient).newCall(requestCaptor.capture());
        
        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertTrue("URL should be token endpoint", 
                capturedRequest.url().toString().contains("/token"));

        String requestBody = getRequestBody(capturedRequest);
        assertTrue("Should contain grant_type", requestBody.contains("grant_type=authorization_code"));
        assertTrue("Should contain code", requestBody.contains("code=" + TEST_AUTH_CODE));
        assertTrue("Should contain code_verifier for PKCE", requestBody.contains("code_verifier="));
        assertFalse("Should not contain client_secret", requestBody.contains("client_secret="));
        assertTrue("Should contain client_id", requestBody.contains("client_id=" + TEST_CLIENT_ID));
        assertTrue("Should contain redirect_uri", requestBody.contains("redirect_uri="));
    }

    private void verifyClientSecretTokenRequest() {
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        verify(mockHttpClient).newCall(requestCaptor.capture());
        
        Request capturedRequest = requestCaptor.getValue();
        String requestBody = getRequestBody(capturedRequest);
        assertTrue("Should contain grant_type", requestBody.contains("grant_type=authorization_code"));
        assertTrue("Should contain code", requestBody.contains("code=" + TEST_AUTH_CODE));
        assertTrue("Should contain client_secret", requestBody.contains("client_secret=" + TEST_CLIENT_SECRET));
        assertFalse("Should not contain code_verifier", requestBody.contains("code_verifier="));
        assertTrue("Should contain client_id", requestBody.contains("client_id=" + TEST_CLIENT_ID));
        assertTrue("Should contain redirect_uri", requestBody.contains("redirect_uri="));
    }

    private void verifyRefreshRequest() {
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        verify(mockHttpClient).newCall(requestCaptor.capture());
        
        Request capturedRequest = requestCaptor.getValue();
        String requestBody = getRequestBody(capturedRequest);
        assertTrue("Should contain grant_type=refresh_token", requestBody.contains("grant_type=refresh_token"));
        assertTrue("Should contain refresh_token", requestBody.contains("refresh_token=" + TEST_REFRESH_TOKEN));
    }

    private void verifyRefreshRequestWithSecret() {
        verifyRefreshRequest();
        
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        verify(mockHttpClient).newCall(requestCaptor.capture());
        
        Request capturedRequest = requestCaptor.getValue();
        String requestBody = getRequestBody(capturedRequest);
        assertTrue("Should contain client_secret", requestBody.contains("client_secret=" + TEST_CLIENT_SECRET));
        assertTrue("Should contain client_id", requestBody.contains("client_id=" + TEST_CLIENT_ID));
    }

    private String getRequestBody(Request request) {
        try {
            RequestBody body = request.body();
            if (body == null) return "";
            
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            return "";
        }
    }
}
