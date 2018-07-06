package com.example.jeason.swipe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeason.swipe.Adapter.ExpandableListCustomAdapter;
import com.example.jeason.swipe.DummyData.ExpandableListData;
import com.example.jeason.swipe.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.example.jeason.swipe.activity.MainActivity.DETAIL_KEY;

public class VideoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_article_list);
        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra(DETAIL_KEY);
        TextView toolBarTitle = findViewById(R.id.toolBarTextView);
        toolBarTitle.setText(itemTitle);
        //TODO,need to work on this activity.
//        Toolbar toolbar = findViewById(R.id.video_activity_detailViewToolBar);
//        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ExpandableListView expandableListView = findViewById(R.id.video_detail_expandableListView);
//        final LinkedHashMap<String, List<String>> expandableListDetail = ExpandableListData.getThirdLevelData();
//        final List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
//        smooth expanding animaiton refer to https://stackoverflow.com/questions/19263312/how-to-achieve-smooth-expand-collapse-animation
//        ExpandableListAdapter expandableListAdapter = new ExpandableListCustomAdapter(this, expandableListTitle, expandableListDetail);
//        expandableListView.setAdapter(expandableListAdapter);
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
//                return false;
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
