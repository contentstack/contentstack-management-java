package com.contentstack.cms.core;

public class Util {

    protected static String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        String operatingSystem = System.getProperty("os.name").toUpperCase();
        return agent != null ? agent : ("Java" + System.getProperty("java.version")+" OS: "+operatingSystem);
    }

}
