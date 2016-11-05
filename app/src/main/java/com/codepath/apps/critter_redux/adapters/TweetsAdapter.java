package com.codepath.apps.critter_redux.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.listeners.OnItemClickListener;
import com.codepath.apps.critter_redux.models.Tweet;
import com.codepath.apps.critter_redux.util.Utilities;

import java.util.List;


public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private Context context;

    private List<Tweet> tweets;

    private static OnItemClickListener listener;
    //Listener interface defined in OnItemClickListener.java




    // Define the method that allows the parent activity to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    /***** Creating ViewHolder *****/

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // holder should contain a member variable
        // for any view that will be set to render a row

        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvHandle;
        public TextView tvTimestamp;
        public TextView tvBody;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.iv_profile_image);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            tvHandle = (TextView) itemView.findViewById(R.id.tv_handle);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);
            tvBody = (TextView) itemView.findViewById(R.id.tv_body);



            // Attach a click listener to the 'row' by bubbling up
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });

        }
    }//end class ViewHolder

    public TweetsAdapter(Context c, List<Tweet> tweets) {
        context = c;
        this.tweets = tweets;
    }

    // Easy access to the context object in the RecyclerView
    private Context getContext() {
        return context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View timelineView = inflater.inflate(R.layout.item_tweet, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(timelineView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Tweet tweet = tweets.get(position);

        // Set item views based on views and data model

        //clear image in case it had a previous value
        holder.ivProfileImage.setImageResource(android.R.color.transparent);//Color.parseColor("#80000000")
        //then load image with Picasso //todo: not for now
//        Picasso.with(getContext()).load(tweet.getUser().getProfileURL())
//                .placeholder(R.drawable.placeholder)
//                .into(holder.ivProfileImage);

        holder.tvUserName.setText(tweet.getUser().getName());

        holder.tvHandle.setText("@"+tweet.getUser().getScreenName());

        holder.tvTimestamp.setText(Utilities.getRelativeTimeAgo(tweet.getCreatedAt()));

        holder.tvBody.setText(tweet.getBody());
    }



    @Override
    public int getItemCount() {
        return tweets.size();
    }



}
