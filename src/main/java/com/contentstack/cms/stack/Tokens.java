package com.contentstack.cms.stack;

import java.util.Map;

import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;

/**
 * Contentstack provides different types of tokens to authorize API requests
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-22
 */
public class Tokens {

    protected final TokenService service;
     protected final Map<String, Object> headers;

    protected Tokens(Retrofit retrofit,Map<String, Object> headers) {
        this.service = retrofit.create(TokenService.class);
        this.headers = headers;
    }

    /**
     * You can use Delivery Tokens to authenticate Content Delivery API (CDA)
     * requests and retrieve the published
     * content of an environment.
     *
     * @return DeliveryToken
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/create-tokens/about-delivery-tokens">About
     * Delivery
     * Tokens
     * </a>
     * @since 0.1.0
     */
    public DeliveryToken deliveryTokens() {
        return new DeliveryToken(this.service,this.headers);
    }

    /**
     * You can use Delivery Tokens to authenticate Content Delivery API (CDA)
     * requests and retrieve the published
     * content of an environment.
     *
     * @param tokenUid The UID of the token that you want to retrieve a delivery
     *                 token
     * @return DeliveryToken
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/create-tokens/about-delivery-tokens">About
     * Delivery
     * Tokens
     * </a>
     * @since 0.1.0
     */
    public DeliveryToken deliveryTokens(@NotNull String tokenUid) {
        return new DeliveryToken(this.service,this.headers, tokenUid);
    }

    /**
     * To authenticate Content Management API (CMA) requests over your stack
     * content, you can use Management Tokens
     *
     * @return ManagementToken
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/create-tokens/about-management-tokens">About
     * Management Tokens
     * </a>
     * @since 0.1.0
     */
    public ManagementToken managementToken() {
        return new ManagementToken(this.service,this.headers);
    }

    /**
     * To authenticate Content Management API (CMA) requests over your stack
     * content, you can use Management Tokens
     *
     * @param managementTokenUid the managementTokenUid
     * @return ManagementToken
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/create-tokens/about-management-tokens">About
     * Management Tokens
     * </a>
     * @since 0.1.0
     */
    public ManagementToken managementToken(@NotNull String managementTokenUid) {
        return new ManagementToken(this.service,this.headers, managementTokenUid);
    }

}
