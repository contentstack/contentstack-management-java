package com.contentstack.cms;

import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.LauncherSessionListener;

/**
 * JUnit Platform session hooks for the dynamic test stack.
 *
 * <p>Registered via ServiceLoader
 * ({@code META-INF/services/org.junit.platform.launcher.LauncherSessionListener}),
 * so it runs for the full surefire invocation as well as single-class IDE runs
 * - the equivalent of the JS sanity suite's global {@code before()}.
 *
 * <p>Does nothing unless {@code DYNAMIC_STACK=true}. Setup is also triggered
 * lazily by {@link TestClient}, so this listener mainly guarantees eager,
 * clearly-logged setup before any test class loads.
 *
 * <p><b>Teardown deliberately does NOT happen here.</b> The vintage
 * {@code @RunWith(JUnitPlatform.class)} suite runner opens a nested launcher
 * session, and its {@code launcherSessionClosed} fires while the outer run is
 * still executing - tearing down there deletes the stack mid-run (observed as
 * mass 412 "We can't find that Stack" failures). Teardown is handled solely by
 * the JVM shutdown hook that {@link TestStackContext#ensureSetup()} registers.
 */
public class TestSuiteLifecycle implements LauncherSessionListener {

    @Override
    public void launcherSessionOpened(LauncherSession session) {
        if (TestStackContext.isDynamicMode()) {
            TestStackContext.ensureSetup();
        }
    }

    @Override
    public void launcherSessionClosed(LauncherSession session) {
        // Stack teardown deliberately does NOT happen here - see class javadoc;
        // it runs in the JVM shutdown hook so nested launcher sessions can't
        // kill the stack early. Refreshing the custom report here is safe:
        // it just overwrites a file, and the shutdown hook writes the final one.
        TestReporter.writeReport();
    }
}
