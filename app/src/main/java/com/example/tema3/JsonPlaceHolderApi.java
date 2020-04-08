package com.example.tema3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/todos")
    Call<List<ToDo>>getToDosForUser(@Query("userId")int user_id);
}

