package com.example.facefy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.facefy.ItemFragment.OnListFragmentInteractionListener;
import com.example.facefy.model.EventsModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EventsModel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<EventsModel> mValues;
    private final OnListFragmentInteractionListener mListener;


    public MyItemRecyclerViewAdapter(List<EventsModel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.eventsName.setText(mValues.get(position).name);
        holder.eventsValue.setText(mValues.get(position).value);
        //EventViewHolder.eventsPhoto.setImageResource(events.get(i).photoId);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public EventsModel mItem;

        public final TextView eventsName;
        public final TextView eventsValue;
        public final ImageView eventsPhoto;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            eventsName = (TextView)itemView.findViewById(R.id.event_name);
            eventsValue = (TextView)itemView.findViewById(R.id.event_value);
            eventsPhoto = (ImageView)itemView.findViewById(R.id.event_photo);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + eventsName.getText() + "'";
        }
    }
}
