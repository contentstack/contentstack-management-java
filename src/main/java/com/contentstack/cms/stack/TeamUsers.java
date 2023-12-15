package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;

import java.util.HashMap;

/**
 * Teams streamline the process of assigning roles and permissions by grouping users together.
 * Rather than assigning roles to individual users or at the stack level,
 * you can assign roles directly to a team.
 * This ensures that all users within a team share the same set of role permissions,
 * making role management more efficient.
 */
public class TeamUsers implements BaseImplementation<TeamUsers> {

    private HashMap<String, Object> headers;
    private HashMap<String, Object> params;
    final TeamService teamService;

    public TeamUsers(TeamService service, HashMap<String, Object> headers, @NotNull String teamId) {
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.teamService = service;
    }

    @Override
    public TeamUsers addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    @Override
    public TeamUsers addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public TeamUsers addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    public TeamUsers addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }


    public Call<ResponseBody> create(@NotNull JSONObject body) {
        return this.teamService.create(this.headers, body);
    }

//    public Call<ResponseBody> find() {
//        return this.teamService.find(this.headers, body);
//    }
//
//    public Call<ResponseBody> delete(@NotNull String teamId) {
//        return this.teamService.delete(this.headers, teamId);
//    }

}
