package com.omarmohamed.githubuserlist.utils;

/**
 * Utility class that contains all the constants used inside the app (regexp, generic constants, urls, etc.)
 * Created by omarmohamed on 17/08/2016.
 */
public class Constants {

    /**
     * Inner class that contains all the urls needed inside the app
     */
    public static class URLs {
        public static String BASE_URL_GITHUB = "https://api.github.com/";
    }

    /**
     * Constants used for pagination purpose
     */
    public static class Pagination {
        public static int USERS_PER_PAGE = 200;
        public static int INITIAL_USERS_PER_PAGE = 30;
    }
}
