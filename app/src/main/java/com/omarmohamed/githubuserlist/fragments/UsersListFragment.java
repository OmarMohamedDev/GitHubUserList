package com.omarmohamed.githubuserlist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
 * interface.
 */
public class UsersListFragment extends Fragment {

    /**
     * Listener used to interact with the elements inside the recycler view
     */
    private OnItemClickListener mOnItemClickListener;

    /**
     * List of users
     */
    private List<User> mUserList;

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
        mUserList = new ArrayList<>();

        //Setting up the recyclerview
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //if the the device where the app runs is connected to the internet, we try to retrieve
        //the data from the github servers, otherwise we ask to the user to connect and retry
        //if (Utilities.hasActiveInternetConnection(context)) {
        if (Utilities.hasActiveInternetConnection(context)) {
            //Retrieving the userlist
            mUserList = Utilities.retrieveGithubUsers();
        } else {
            Snackbar.make(view, R.string.network_not_available, Snackbar.LENGTH_INDEFINITE).show();
            //TODO: Manage the empty list case (at the moment we just don't display nothing)
            //TODO: Check periodically if the user connected to the internet in order to load the list
            //TODO: Add progress bar when loading the list of users
        }

        //Setting up the onItemClickListener
        mOnItemClickListener = new UsersListFragment.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUserList.get(position).getHtmlUrl()));
                getActivity().startActivity(browserIntent);
            }
        };

        //Setting up the onItemTouchListener to avoid unexpected behavior and give the chance to customize
        //the touch events to improve the UX
        //TODO: Refactor onItemClick / onItemTouch mechanism
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mOnItemClickListener));
        //Setting up the adapter
        recyclerView.setAdapter(new UserAdapter(mUserList, getActivity(), mOnItemClickListener));
        return view;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        GestureDetector mGestureDetector;
        private OnItemClickListener mListener;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
