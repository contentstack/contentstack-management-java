package com.contentstack.cms.stack;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


/**
 * The type Stack.
 */
public class Stack {

    private final StackService orgService;

    /*
     * Instantiates a new Stack.
     *
     * @param client the client
     */
    public Stack(@NotNull Retrofit client) {
        this.orgService = client.create(StackService.class);
    }


    /*
     * <b>Get a single stack</b>
     * <br>
     * The Get a single stack call fetches comprehensive details of a specific stack
     * <br>
     * <b>Note:</b> For SSO-enabled organizations,
     * it is mandatory to pass the organization UID in the header.
     *
     * @param apiKey the api key
     * @return the stack
     */
    public Call<ResponseBody> singleStack(@NotNull String apiKey) {
        return this.orgService.singleStack(apiKey);
    }


    /*
     * <b>Get a single stack</b>
     * <br>
     * The Get a single stack call fetches comprehensive details of a specific stack
     * <br>
     * <b>Note:</b> For SSO-enabled organizations,
     * it is mandatory to pass the organization UID in the header.
     *
     * @param apiKey          the api key
     * @param organizationUid the organization uid
     * @return the stack
     */
    public Call<ResponseBody> singleStack(
            @NotNull String apiKey,
            @NotNull String organizationUid) {
        return this.orgService.singleStack(apiKey, organizationUid);
    }


    /*
     * <b>Get a single stack</b>
     * <br>
     * The Get a single stack call fetches comprehensive details of a specific stack
     * <br>
     * <b>Note:</b> For SSO-enabled organizations,
     * it is mandatory to pass the organization UID in the header.
     *
     * @param apiKey the api key
     * @param orgUID the organization uid
     * @param query  the query param
     * @return the stack
     */
    public Call<ResponseBody> singleStack(
            @NotNull String apiKey,
            @NotNull String orgUID,
            @NotNull HashMap<String, Boolean> query) {
        return this.orgService.singleStack(apiKey, orgUID, query);
    }


    /*
     * <b>Get all stacks</b>
     * <br>
     * The Get all stacks call fetches the list of all stacks owned by and shared with a
     * particular user account
     * <br>
     * <b>Note:</b> Note: For SSO-enabled organizations,
     * it is mandatory to pass the organization UID in the header.
     * <br>
     *
     * @return the organization role
     *
     * public Call<ResponseBody> allStacks() { return orgService.allStacks();}
     */

    /*
     * <b>Create stack.</b>
     * <p>
     * The Create stack call creates a new stack in your Contentstack account.
     * <p>
     * In the 'Body' section, provide the schema of the stack in JSON format
     * <p>
     * <b>Note:</b>At any given point of time, an organization can create only one stack per minute.
     *
     * @param organizationUid the organization uid
     * @param bodyString      the body string
     * @return the stack
     */
    public Call<ResponseBody> createStack(
            @NotNull String organizationUid,
            @NotNull String bodyString) {
        RequestBody body = toBody(bodyString);
        return orgService.createStack(organizationUid, body);
    }

    /*
     * <b>Update Stack</b>
     * <br>
     * The Update stack call lets you update the name and description of an existing stack.
     * <br>
     * In the 'Body' section, provide the updated schema of the stack in JSON format.
     * <br>
     * <b>Note</b> Warning: The master locale cannot be changed once it is set
     * while stack creation. So, you cannot use this call to change/update
     * the master language.
     *
     * @param apiKey     the api key
     * @param bodyString the body string
     * @return the stack
     */
    public Call<ResponseBody> updateStack(@NotNull String apiKey, String bodyString) {
        RequestBody body = toBody(bodyString);
        return orgService.updateStack(apiKey, body);
    }

    /*
     * <b>Transfer Stack Ownership</b>
     * <br>
     * The Transfer stack ownership to other users call sends the
     * specified user an email invitation for accepting the
     * ownership of a particular stack.
     * <br>
     * Once the specified user accepts the invitation by clicking
     * on the link provided in the email, the ownership of the
     * stack gets transferred to the new user. Subsequently,
     * the previous owner will no longer have any permission on the stack.
     * <br>
     * <b>Note</b> Warning: The master locale cannot be changed once it is set
     * while stack creation. So, you cannot use this call to change/update
     * the master language.
     *
     * @param apiKey     the api key
     * @param bodyString the body string
     * @return the stack
     */
    public Call<ResponseBody> transferOwnership(String apiKey, String bodyString) {
        RequestBody body = toBody(bodyString);
        return orgService.transferOwnership(apiKey, body);
    }


    private RequestBody toBody(String bodyString) {
        return RequestBody.
                create(MediaType.parse("application/json; charset=UTF-8"),
                        bodyString);
    }


}
