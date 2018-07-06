package com.example.jeason.swipe.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeason.swipe.R;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private ArrayList<String> simplifiedChinese;
    private int selectedPosition = -1;
    private Context context;
    private int rowHighlightUpdateTracker;
//    private ArrayList<String> pinYin;
//    private ArrayList<String> english;

   public WordAdapter(ArrayList<String> simplifiedChinese, Context context) {
        this.simplifiedChinese = simplifiedChinese;
//        this.pinYin = pinYin;
//        this.english = english;
        this.context = context;

    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int row_layout = R.layout.mantra_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(row_layout, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        //region Content to display
        String simplifiedChineseCharacterToDisplay = simplifiedChinese.get(position);
//        String pinYinToDisplay = pinYin.get(position);
//        String englishToDisplay = english.get(position);
        //endregion
        //region Content Holder
        holder.simplifiedChineseCharacter.setText(simplifiedChineseCharacterToDisplay);
//        holder.chinesePinYin.setText(pinYinToDisplay);
//        holder.english.setText(englishToDisplay);
        //endregion
        holder.idTextView.setText(String.valueOf(position));
        //region Highlight selected item.
        holder.itemView.setBackgroundColor(selectedPosition == position ? ContextCompat.getColor(context, R.color.colorAccent) : Color.TRANSPARENT);
//        holder.simplifiedChineseCharacter.setTextColor(selectedPosition == position ? ContextCompat.getColor(context, R.color.icons) :
//                ContextCompat.getColor(context, R.color.primary));
        //endregion
        //region Highlight item
        holder.simplifiedChineseCharacter.setTextColor(position == rowHighlightUpdateTracker ? ContextCompat.getColor(context, R.color.colorAccent) : ContextCompat.getColor(context, R.color.colorPrimary));
        //endregion
    }

    public void setRowHighlightUpdateTracker(int rowHighlightUpdateTracker) {
        this.rowHighlightUpdateTracker = rowHighlightUpdateTracker;
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (null == simplifiedChinese) {
            return 0;
        } else {
            return simplifiedChinese.size();
        }

    }

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView simplifiedChineseCharacter, idTextView, chinesePinYin;

        WordViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            simplifiedChineseCharacter = itemView.findViewById(R.id.sanskritItemView);
//            chinesePinYin = itemView.findViewById(R.id.ChinesePinYin);
//            english = itemView.findViewById(R.id.english);
            idTextView = itemView.findViewById(R.id.lineID);
        }

        @Override
        public void onClick(View view) {

        }
    }

}