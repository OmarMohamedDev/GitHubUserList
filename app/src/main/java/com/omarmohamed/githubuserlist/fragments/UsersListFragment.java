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

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;
import com.omarmohamed.githubuserlist.R;
import com.omarmohamed.githubuserlist.models.User;
import com.omarmohamed.githubuserlist.utils.Constants;
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

    private int mUsersAlreadyLoaded;

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

        mUsersAlreadyLoaded = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        //Declaring and initializing an empty userlist that will be used by the adapter
        mUserList = new ArrayList<>();

        //Setting up the recyclerview
        Context context = view.getContext();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //if the the device where the app runs is connected to the internet, we try to retrieve
        //the data from the github servers, otherwise we ask to the user to connect and retry
        //if (Utilities.hasActiveInternetConnection(context)) {
        if (Utilities.hasActiveInternetConnection(context)) {
            //Retrieving the userlist
            mUserList = Utilities.retrieveGithubUsers(mUsersAlreadyLoaded);
            mUsersAlreadyLoaded += Constants.Pagination.USERS_PER_PAGE;
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

        //mCollectionView can be a ListView, GridView, RecyclerView or any instance of AbsListView!
        BaseAttacher attacher = Mugen.with(recyclerView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                /* Will be triggered when the next page has to be loaded.
                *
                * Do your load operation here.
                * Note: this is NOT asynchronous!
                */
                // mUserList.clear();
                mUserList.addAll(Utilities.retrieveGithubUsers(mUsersAlreadyLoaded / Constants.Pagination.USERS_PER_PAGE));
                mUsersAlreadyLoaded += Constants.Pagination.USERS_PER_PAGE;
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public boolean isLoading() {
                /* Return true if a load operation is ongoing. This will
                * be used as an optimization to prevent further triggers
                * if the user scrolls up and scrolls back down before
                * the load operation finished.
                *
                * If there is no load operation ongoing, return false
                */
                return false; //Check
            }

            @Override
            public boolean hasLoadedAllItems() {
                /*
                * If every item has been loaded from the data store, i.e., no more items are
                * left to fetched, you can start returning true here to prevent any more
                * triggers of the load more method as a form of optimization.
                *
                * This is useful when say, the data is being fetched from the network
                */
                return false;
            }
        }).start();

        /* Use this to dynamically turn infinite scroll on or off. It is enabled by default */
        attacher.setLoadMoreEnabled(true);

        /* Use this to change when the onLoadMore() function is called.
        * By default, it is called when the scroll reaches 2 items from the bottom */
        attacher.setLoadMoreOffset(4);


        //Setting up the onItemTouchListener to avoid unexpected behavior and give the chance to customize
        //the touch events to improve the UX
        //TODO: Refactor onItemClick / onItemTouch mechanism
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mOnItemClickListener));
        //Setting up the adapter
        if (mUserList == null) {
            mUserList = new ArrayList<>();
            Snackbar.make(view, R.string.users_list_not_available, Snackbar.LENGTH_INDEFINITE).show();
        }
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
