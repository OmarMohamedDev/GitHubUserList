package com.omarmohamed.githubuserlist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omarmohamed.githubuserlist.R;
import com.omarmohamed.githubuserlist.models.User;
import com.omarmohamed.githubuserlist.utils.Utilities;
import com.omarmohamed.githubuserlist.views.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class UsersListFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UsersListFragment() {
    }

    public static UsersListFragment newInstance() {
        return new UsersListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        //Declaring and initializing an empty userlist that will be used by the adapter
        List<User> userList = new ArrayList<>();

        //Setting up the recyclerview
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //if the the device where the app runs is connected to the internet, we try to retrieve
        //the data from the github servers, otherwise we ask to the user to connect and retry
        if (Utilities.hasActiveInternetConnection(context)) {
            //Retrieving the userlist
            userList = Utilities.retrieveGithubUsers();
        } else {
            Snackbar.make(view, R.string.network_not_available, Snackbar.LENGTH_INDEFINITE).show();
            //TODO: Manage the empty list case (at the moment we just don't display nothing)
            //TODO: Check periodically if the user connected to the internet in order to load the list
        }

        //Setting up the adapter
        recyclerView.setAdapter(new UserAdapter(userList, mListener));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(User item);
    }
}
