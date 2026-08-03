package com.contentstack.cms;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Generates human-readable display names for every test that has no explicit
 * {@code @DisplayName} - e.g. {@code testAllInvitationWithQuery()} becomes
 * "should fetch all invitation with query". Registered globally via
 * {@code junit.jupiter.displayname.generator.default} in
 * {@code junit-platform.properties}; explicit annotations always win.
 */
public class TestDisplayNameGenerator extends DisplayNameGenerator.Standard {

    /** verbs that read naturally with a "should <verb> ..." prefix */
    private static final Set<String> VERBS = new HashSet<>(Arrays.asList(
            "create", "fetch", "find", "get", "update", "delete", "upload", "replace",
            "publish", "unpublish", "localize", "unlocalize", "move", "import", "export",
            "clone", "deploy", "share", "unshare", "transfer", "accept", "reject",
            "login", "logout", "validate", "verify", "handle", "return", "reorder",
            "generate", "download", "setup", "throw", "reset", "activate", "deactivate"));

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return splitCamelCase(testClass.getSimpleName());
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        String name = testMethod.getName();
        // strip common prefixes
        if (name.startsWith("test")) {
            name = name.substring(4);
        }
        String sentence = splitCamelCase(name).toLowerCase();
        if (sentence.isEmpty()) {
            return testMethod.getName();
        }
        String firstWord = sentence.split(" ")[0];
        if (VERBS.contains(firstWord)) {
            return "should " + sentence;
        }
        // capitalize the first letter for non-verb starts, e.g. "All invitation with query"
        return Character.toUpperCase(sentence.charAt(0)) + sentence.substring(1);
    }

    private static String splitCamelCase(String s) {
        return s
                // lower-to-upper boundary: fetchAll -> fetch All
                .replaceAll("([a-z0-9])([A-Z])", "$1 $2")
                // acronym-to-word boundary: APITest -> API Test
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1 $2")
                // letter-digit boundary: fetch2 -> fetch 2
                .replaceAll("([a-zA-Z])(\\d)", "$1 $2")
                .replace('_', ' ')
                .trim();
    }
}
