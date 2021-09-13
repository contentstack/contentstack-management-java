package com.contentstack.cms.core;

public class ResponseData<T> {

    /**
     * The result contained by this response
     */
    private T result;

    /**
     * Additional CMS metadata for this response
     */
    private ResponseMetadata responseMetadata;

    /**
     * Returns the result contained by this response.
     *
     * @return The result contained by this response.
     */
    public T getResult() {
        return result;
    }

    /**
     * Sets the result contained by this response.
     *
     * @param result The result contained by this response.
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * Sets the response metadata associated with this response.
     *
     * @param responseMetadata The response metadata for this response.
     */
    public void setResponseMetadata(ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
    }

    /**
     * Returns the response metadata for this response. Response metadata
     * provides additional information about a response that isn't necessarily
     * directly part of the data the service is returning. Response metadata is
     * primarily used for debugging issues with CMS support when a service isn't
     * working as expected.
     *
     * @return The response metadata for this response.
     */
    public ResponseMetadata getResponseMetadata() {
        return responseMetadata;
    }

    /**
     * Returns the CMS request ID from the response metadata section of an CMS
     * response.
     *
     * @return The CMS request ID from the response metadata section of an CMS
     * response.
     */
    public String getRequestId() {
        if (responseMetadata == null) return null;
        return responseMetadata.getRequestId();
    }
}
