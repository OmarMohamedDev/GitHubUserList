package com.omarmohamed.githubuserlist.network;

import com.omarmohamed.githubuserlist.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface that represent the GitHub service
 * Created by omarmohamed on 17/08/2016.
 */

public interface GitHubService {
    @GET("users")
    Call<List<User>> listUsers(@Query("since") int page);
}