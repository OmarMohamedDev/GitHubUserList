package com.omarmohamed.githubuserlist.activities;

import android.app.Activity;
import android.os.Bundle;

import com.omarmohamed.githubuserlist.R;
import com.omarmohamed.githubuserlist.fragments.UsersListFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add the list fragment to the container
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, UsersListFragment.newInstance(1)).commit();
    }
}
