package com.contentstack.cms.user;
import retrofit2.Call;
import retrofit2.Retrofit;

public class User<T> {

    private UserService userService;

    private User(){}

    public User(Retrofit client) { this.userService = client.create(UserService.class); }

    public Call getUser(Class<T> type){ return this.userService.getUser(); }

    public Call updateUser(){ return userService.updateUser(); }
}
