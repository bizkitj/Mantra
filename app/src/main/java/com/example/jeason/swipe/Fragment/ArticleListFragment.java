package com.example.jeason.swipe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.jeason.swipe.Adapter.ExpandableListCustomAdapter;
import com.example.jeason.swipe.DummyData.ExpandableListData;
import com.example.jeason.swipe.R;
import com.example.jeason.swipe.activity.ArticleDetailActivity;
import com.example.jeason.swipe.activity.MantraDetailActivity;

import static com.example.jeason.swipe.activity.MainActivity.DETAIL_KEY;

/**
 * Created by Jeason on 2018/3/6.
 */

public class ArticleListFragment extends Fragment {

    private String TAG = ArticleListFragment.class.getSimpleName();

    public static ArticleListFragment newInstance() {
        ArticleListFragment fragment = new ArticleListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article_list, container, false);
        Log.v(TAG, "onCreateView");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ExpandableListView expandableListView = view.findViewById(R.id.video_detail_expandableListView);
        ExpandableListCustomAdapter listCustomAdapter = new ExpandableListCustomAdapter(getContext(), ExpandableListData.getTitle(), ExpandableListData.getData());
        expandableListView.setAdapter(listCustomAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                TextView textView = view.findViewById(R.id.expandedListItem);
                Intent toArticleDetailIntent = new Intent(getActivity(), ArticleDetailActivity.class);
                toArticleDetailIntent.putExtra(DETAIL_KEY,textView.getText().toString());
                startActivity(toArticleDetailIntent);
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach");

    }
}
