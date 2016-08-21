package com.omarmohamed.githubuserlist.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omarmohamed.githubuserlist.R;
import com.omarmohamed.githubuserlist.fragments.UsersListFragment;
import com.omarmohamed.githubuserlist.models.User;
import com.omarmohamed.githubuserlist.network.DownloadImageTask;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User}
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<User> mValues;
    private final UsersListFragment.OnItemClickListener mOnItemClickListener;

    public UserAdapter(List<User> items, UsersListFragment.OnItemClickListener onItemClickListener) {
        mValues = items;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mUser = mValues.get(position);
        //holder.mAvatarView.setImageResource(R.mipmap.ic_launcher);
        //Setting up dinamically the relative avatar image from URL
        new DownloadImageTask(holder.mAvatarView).execute(mValues.get(position).getAvatarUrl());
        holder.mNameView.setText(mValues.get(position).getLogin());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener) {
                    // Notify the active callbacks interface that an item has been selected.
                    holder.bind(holder.mUser, holder.mView, position, mOnItemClickListener);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mAvatarView;
        public final TextView mNameView;
        public User mUser;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatarView = (ImageView) view.findViewById(R.id.avatar);
            mNameView = (TextView) view.findViewById(R.id.name);
        }

        public void bind(final User user, final View view, final int position, final UsersListFragment.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(view, position);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

}
