package com.omarmohamed.githubuserlist.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.omarmohamed.githubuserlist.R;
import com.omarmohamed.githubuserlist.fragments.UsersListFragment;

/**
 * Main activity of the app that simply contain a fragment container
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add the list fragment to the container
        getFragmentManager().beginTransaction().add(R.id.fragment_container, new UsersListFragment()).commit();
    }
}
