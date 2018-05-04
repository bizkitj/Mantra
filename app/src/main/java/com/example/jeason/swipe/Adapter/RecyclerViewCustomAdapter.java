package com.example.jeason.swipe.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeason.swipe.Interface.Fragment2ActivityCommunicator;
import com.example.jeason.swipe.R;

/**
 * Created by Jeason on 2018/3/6.
 */
//refer to https://stackoverflow.com/questions/15444375/how-to-create-interface-between-fragment-and-adapter/37535113#37535113
//refer to https://stackoverflow.com/questions/40672426/click-on-an-item-in-recyclerview-contained-in-a-fragment-to-open-new-activity-wi
public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private String[]  mDataSet;
    private Fragment2ActivityCommunicator fragment2ActivityCommunicatorListener;

    public RecyclerViewCustomAdapter(String[]  dataSet, Fragment2ActivityCommunicator callback) {
        fragment2ActivityCommunicatorListener = callback;
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        TextView textView = viewHolder.getTextView();
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        textView.setText(mDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textView;

        ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            textView.setOnClickListener(this);
        }

        TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View view) {
            fragment2ActivityCommunicatorListener.dataFromFragmentBackToActivity(view);
            Log.v(TAG, "Clicked position # " + String.valueOf(getAdapterPosition()));
        }
    }

}