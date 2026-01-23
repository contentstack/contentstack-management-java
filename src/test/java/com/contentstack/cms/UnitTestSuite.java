package com.contentstack.cms;

import com.contentstack.cms.core.AuthInterceptorTest;
import com.contentstack.cms.stack.EnvironmentUnitTest;
import com.contentstack.cms.stack.GlobalFieldUnitTests;
import com.contentstack.cms.stack.LocaleUnitTest;
import com.contentstack.cms.stack.ReleaseUnitTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
 * Unit Test Suite for running all unit tests
 * These tests don't require API access or credentials
 * 
 * Note: Only public test classes can be included here.
 * Many unit test classes in the project are package-private and 
 * cannot be referenced in this suite.
 */
@SuppressWarnings("deprecation")
@RunWith(JUnitPlatform.class)
@SelectClasses({
        // Core tests
        AuthInterceptorTest.class,
        ContentstackUnitTest.class,
        
        // Stack module tests (only public classes)
        EnvironmentUnitTest.class,
        GlobalFieldUnitTests.class,
        LocaleUnitTest.class,
        ReleaseUnitTest.class
})
public class UnitTestSuite {
}

