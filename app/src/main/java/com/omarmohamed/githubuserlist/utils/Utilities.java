package com.omarmohamed.githubuserlist.utils;

import com.omarmohamed.githubuserlist.models.User;
import com.omarmohamed.githubuserlist.network.RetrieveUsersListTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Utility class that contains useful generic methods used inside the app
 * Created by omarmohamed on 17/08/2016.
 */
public class Utilities {

    /**
     * Retrieve the list of all the GitHub users
     *
     * @return a List of User Objects
     */
    public static List<User> retrieveGithubUsers() {
        try {
            return new RetrieveUsersListTask().execute().get();
            //TODO: Manage catch properly
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
