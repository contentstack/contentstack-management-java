package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * Teams streamline the process of assigning roles and permissions by grouping users together.
 * Rather than assigning roles to individual users or at the stack level,
 * you can assign roles directly to a team.
 * This ensures that all users within a team share the same set of role permissions,
 * making role management more efficient.
 */
public class Teams implements BaseImplementation<Teams> {

    private HashMap<String, Object> headers;
    private HashMap<String, Object> params;
    final TeamService teamService;

    public Teams(Retrofit client, HashMap<String, Object> headers) {
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.teamService = client.create(TeamService.class);
    }

    @Override
    public Teams addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    @Override
    public Teams addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public Teams addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    public Teams addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }


//    /**
//     *
//     * @return Call that conatains response body
//     */
//    public Call<ResponseBody> find() {
//        return this.teamService.find(this.headers, this.params);
//    }
//
//    public Call<ResponseBody> create(@NotNull JSONObject body) {
//        return this.teamService.create(this.headers, body);
//    }
//
//    public Call<ResponseBody> fetch(@NotNull String teamId) {
//        return this.teamService.fetch(this.headers, teamId);
//    }
//
//    public Call<ResponseBody> update(@NotNull String teamId, @NotNull JSONObject body) {
//        return this.teamService.update(this.headers, teamId, body);
//    }
//
//    public Call<ResponseBody> delete(@NotNull String teamId) {
//        return this.teamService.delete(this.headers, teamId);
//    }
//
//    public TeamUsers users(@NotNull String teamId) {
//        return new TeamUsers(this.teamService, this.headers, teamId);
//    }
//
//    public StackRoleMapping stackRoleMapping(@NotNull String teamId) {
//        return new StackRoleMapping(this.teamService, this.headers, teamId);
//    }


}
