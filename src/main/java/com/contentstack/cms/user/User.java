package com.contentstack.cms.user;

import retrofit2.Call;
import retrofit2.Retrofit;

// TODO: create documentation
public class User {

    private UserService userService;

    private User(){ }

    public User(Retrofit client) { this.userService = client.create(UserService.class); }

    public Call getUser(){ return this.userService.getUser(); }

    public Call updateUser(){ return userService.updateUser(); }

    public Call activateUser(){ return userService.updateUser(); }

    public Call requestPassword(){ return userService.updateUser(); }

    public Call resetPassword(){ return userService.updateUser(); }

    public Call logoutWithAuthtoken(String authtoken){ return userService.logout(authtoken); }

    public Call logout(){ return userService.logout(); }

}
