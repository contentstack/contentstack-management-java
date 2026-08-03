package com.contentstack.cms.user;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.models.LoginDetails;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Two-Factor Authentication (2FA/TOTP) login tests - the Java port of the JS
 * suite's "Two-Factor Authentication (2FA/TOTP)" block in user-test.js.
 *
 * <p>Requires a 2FA-enabled test account: TFA_EMAIL, TFA_PASSWORD and
 * MFA_SECRET in the environment. Every test skips gracefully when absent.
 * Each test builds a fresh client (login throws if already logged in).
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TfaLoginAPITest {

    private static String tfaEmail;
    private static String tfaPassword;
    private static String mfaSecret;

    @BeforeAll
    void setup() {
        tfaEmail = TestClient.TFA_EMAIL;
        tfaPassword = TestClient.TFA_PASSWORD;
        mfaSecret = TestClient.MFA_SECRET;
        Assumptions.assumeTrue(tfaEmail != null && tfaPassword != null,
                "Skipping 2FA tests: TFA_EMAIL / TFA_PASSWORD not configured");
    }

    private Contentstack freshClient() {
        return new Contentstack.Builder().setHost(TestClient.DEV_HOST).build();
    }

    @Test
    @Order(1)
    @DisplayName("should fail login with invalid tfa_token format")
    void testInvalidTfaTokenFormat() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("tfaToken", "not-a-number");
        try {
            Response<LoginDetails> response = freshClient().login(tfaEmail, tfaPassword, params);
            Assertions.assertFalse(response.isSuccessful(),
                    "Login must not succeed with a non-numeric tfa_token");
        } catch (IllegalArgumentException expected) {
            // SDK-side validation rejecting the malformed token is equally correct
            Assertions.assertNotNull(expected.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("should fail login without tfa_token when 2FA is required")
    void testMissingTfaTokenWhenRequired() throws IOException {
        Response<LoginDetails> response = freshClient().login(tfaEmail, tfaPassword);
        Assertions.assertFalse(response.isSuccessful(),
                "Plain login on a 2FA-enabled account must not succeed");
        // NOTE: the SDK consumes the error body internally during login, so we
        // assert on the status code. Contentstack signals "2FA required" with
        // the custom 294 status; a 4xx is also acceptable.
        int code = response.code();
        Assertions.assertTrue(code == 294 || (code >= 400 && code < 500),
                "Expected 294 (2FA required) or 4xx, got: " + code);
    }

    @Test
    @Order(3)
    @DisplayName("should fail login with an incorrect 6-digit tfa_token")
    void testIncorrectSixDigitToken() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("tfaToken", "000000");
        try {
            Response<LoginDetails> response = freshClient().login(tfaEmail, tfaPassword, params);
            Assertions.assertFalse(response.isSuccessful(),
                    "Login must not succeed with a wrong 6-digit tfa_token");
            // SDK consumes the error body internally - assert on the code
            int code = response.code();
            Assertions.assertTrue(code == 294 || (code >= 400 && code < 500),
                    "Expected 294 or 4xx for a wrong tfa_token, got: " + code);
        } catch (IllegalArgumentException sdkRejected) {
            // acceptable: SDK-side rejection before hitting the API
            Assertions.assertNotNull(sdkRejected.getMessage());
        }
    }

    @Test
    @Order(4)
    @DisplayName("should accept login with mfaSecret parameter (TOTP generation)")
    void testMfaSecretTotpLogin() throws IOException {
        Assumptions.assumeTrue(mfaSecret != null && !mfaSecret.isEmpty(),
                "Skipping: MFA_SECRET not configured");
        Map<String, String> params = new HashMap<>();
        params.put("mfaSecret", mfaSecret);
        Response<LoginDetails> response = freshClient().login(tfaEmail, tfaPassword, params);
        Assertions.assertTrue(response.isSuccessful(),
                "TOTP login via mfaSecret should succeed, got " + response.code());
        Assertions.assertNotNull(response.body());
        Assertions.assertNotNull(response.body().user.authtoken,
                "Successful 2FA login must return an authtoken");
    }

    @Test
    @Order(5)
    @DisplayName("should reject providing both tfaToken and mfaSecret")
    void testBothTokenAndSecretRejected() {
        Map<String, String> params = new HashMap<>();
        params.put("tfaToken", "123456");
        params.put("mfaSecret", "SOMESECRET");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> freshClient().login(tfaEmail, tfaPassword, params),
                "SDK must reject ambiguous 2FA input (both tfaToken and mfaSecret)");
    }
}
