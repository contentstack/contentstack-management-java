package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.organization.Organization;
import com.contentstack.cms.user.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StackUnitTests {

    private Organization organization;
    private static String defaultAuthtoken;

    @BeforeAll
    public void setUp() {
        Dotenv dotenv = Dotenv.load();
        defaultAuthtoken = dotenv.get("auth_token");
        assert defaultAuthtoken != null;
        organization = new Contentstack.Builder().setAuthtoken(defaultAuthtoken).build().organization();
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException {
        Constructor<User> constructor = User.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }


}
