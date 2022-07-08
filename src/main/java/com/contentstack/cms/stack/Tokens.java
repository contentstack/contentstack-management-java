package com.contentstack.cms.stack;

import retrofit2.Retrofit;

import java.util.HashMap;


/**
 * <b>Tokens</b>
 * <br>
 * Contentstack provides different types of tokens to authorize API requests
 *
 * @author ishaileshmishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Tokens {

    protected final TokenService service;
    protected HashMap<String, Object> headers;

    protected Tokens(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.service = retrofit.create(TokenService.class);
    }


    /**
     * You can use Delivery Tokens to authenticate Content Delivery API (CDA) requests and retrieve the published
     * content of an environment.
     *
     * @return DeliveryToken
     * @see <a href="https://www.contentstack.com/docs/developers/create-tokens/about-delivery-tokens">About Delivery
     * Tokens
     * </a>
     * @since 1.0.0
     */
    public DeliveryToken deliveryTokens() {
        return new DeliveryToken(this.service, this.headers);
    }


    /**
     * To authenticate Content Management API (CMA) requests over your stack content, you can use Management Tokens
     *
     * @return ManagementToken
     * @see <a href="https://www.contentstack.com/docs/developers/create-tokens/about-management-tokens">About
     * Management Tokens
     * </a>
     * @since 1.0.0
     */
    public ManagementToken managementToken() {
        return new ManagementToken(this.service, this.headers);
    }

}
