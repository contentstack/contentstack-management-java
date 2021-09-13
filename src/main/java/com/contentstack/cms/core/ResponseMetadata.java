package com.contentstack.cms.core;

import java.util.Map;

public class ResponseMetadata {

    public static final String AWS_REQUEST_ID = "AWS_REQUEST_ID";
    protected final Map<String, String> metadata;

    /**
     * Instantiates a new Response metadata.
     *
     * @param metadata the metadata
     */
    public ResponseMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    /**
     * Instantiates a new Response metadata.
     *
     * @param originalResponseMetadata the original response metadata
     */
    public ResponseMetadata(ResponseMetadata originalResponseMetadata) {
        this(originalResponseMetadata.metadata);
    }

    /**
     * Gets request id.
     *
     * @return the request id
     */
    public String getRequestId() {
        return metadata.get(AWS_REQUEST_ID);
    }

    @Override
    public String toString() {
        if (metadata == null)
            return "{}";
        return metadata.toString();
    }

}
