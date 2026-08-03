package com.contentstack.cms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry of UIDs created by {@link FixtureSeeder} (and, in later phases,
 * by tests themselves) — the Java equivalent of the JS sanity suite's
 * {@code testData} object in {@code utility/testHelpers.js}.
 *
 * <p>Tests consume what the seeder created instead of hardcoding UIDs
 * (e.g. {@code TestDataRegistry.entryUid("author", 0)} instead of a
 * pre-existing stack entry UID).
 */
public final class TestDataRegistry {

    private static final Map<String, String> environments = new LinkedHashMap<>();
    private static final Map<String, String> locales = new LinkedHashMap<>();
    private static final List<String> contentTypes = new ArrayList<>();
    private static final List<String> globalFields = new ArrayList<>();
    private static final List<String> taxonomies = new ArrayList<>();
    /** contentTypeUid -> ordered list of created entry UIDs */
    private static final Map<String, List<String>> entries = new LinkedHashMap<>();
    private static final List<String> assets = new ArrayList<>();

    private TestDataRegistry() {
    }

    // ---- record (used by the seeder) -------------------------------------------

    static void recordEnvironment(String name, String uid) {
        environments.put(name, uid);
    }

    static void recordLocale(String code, String uid) {
        locales.put(code, uid);
    }

    static void recordContentType(String uid) {
        contentTypes.add(uid);
    }

    static void recordGlobalField(String uid) {
        globalFields.add(uid);
    }

    static void recordTaxonomy(String uid) {
        taxonomies.add(uid);
    }

    static void recordEntry(String contentTypeUid, String entryUid) {
        entries.computeIfAbsent(contentTypeUid, k -> new ArrayList<>()).add(entryUid);
    }

    static void recordAsset(String uid) {
        assets.add(uid);
    }

    // ---- lookup (used by tests and placeholder resolution) ---------------------

    public static String environmentUid(String name) {
        return environments.get(name);
    }

    public static boolean hasEnvironment(String name) {
        return environments.containsKey(name);
    }

    public static List<String> contentTypeUids() {
        return new ArrayList<>(contentTypes);
    }

    public static List<String> globalFieldUids() {
        return new ArrayList<>(globalFields);
    }

    public static List<String> taxonomyUids() {
        return new ArrayList<>(taxonomies);
    }

    /** @return the Nth entry UID created for a content type, or null */
    public static String entryUid(String contentTypeUid, int index) {
        List<String> list = entries.get(contentTypeUid);
        return (list != null && index < list.size()) ? list.get(index) : null;
    }

    /** @return the Nth uploaded asset UID, or null */
    public static String assetUid(int index) {
        return index < assets.size() ? assets.get(index) : null;
    }

    public static String summary() {
        return "locales=" + locales.size()
                + " environments=" + environments.size()
                + " globalFields=" + globalFields.size()
                + " contentTypes=" + contentTypes.size()
                + " taxonomies=" + taxonomies.size()
                + " entries=" + entries.values().stream().mapToInt(List::size).sum()
                + " assets=" + assets.size();
    }
}
