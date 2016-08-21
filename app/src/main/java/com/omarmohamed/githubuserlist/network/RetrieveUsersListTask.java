package com.omarmohamed.githubuserlist.network;

import android.os.AsyncTask;

import com.omarmohamed.githubuserlist.models.User;
import com.omarmohamed.githubuserlist.utils.Constants;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AsyncTask used to retrieve the user list from the GitHub Servers
 * Created by omarmohamed on 19/08/2016.
 */

public class RetrieveUsersListTask extends AsyncTask<Integer, Void, List<User>> {

    protected List<User> doInBackground(Integer... lastUserSeen) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URLs.BASE_URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        try {
            return service.listUsers(lastUserSeen[0]).execute().body();
        } catch (IOException e) {
            //TODO: Setup the try/catch properly
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(User user) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}