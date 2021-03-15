package com.contentstack.cms.core;

public class Util {

    private Util() {
        throw new AssertionError();
    }

    public static <T> T checkNotNull(T reference, String format, Object... args) {
        if (reference == null) {
            throw new NullPointerException(String.format(format, args));
        }
        return reference;
    }

    protected static String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        String operatingSystem = System.getProperty("os.name").toUpperCase();
        return agent != null ? agent : ("Java" + System.getProperty("java.version")+" OS: "+operatingSystem);
    }


}
