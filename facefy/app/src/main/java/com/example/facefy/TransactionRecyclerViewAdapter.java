package com.example.facefy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.facefy.ItemFragment.OnListFragmentInteractionListener;
import com.example.facefy.model.EventsModel;
import com.example.facefy.model.TransactionModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EventsModel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> {

    private final List<TransactionModel> mValues;
    private final OnListFragmentInteractionListener mListener;


    public TransactionRecyclerViewAdapter(List<TransactionModel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        if (mValues.get(position) != null ) {
            holder.transactionDate.setText(mValues.get(position).date);
            holder.transactionAmount.setText(mValues.get(position).amount);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TransactionModel mItem;

        public final TextView transactionDate;
        public final TextView transactionAmount;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            transactionDate = (TextView)itemView.findViewById(R.id.transaction_date);
            transactionAmount = (TextView)itemView.findViewById(R.id.transaction_amount);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + transactionDate.getText() + "'";
        }
    }
}
