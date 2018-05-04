package com.example.jeason.swipe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jeason.swipe.Adapter.SectionsPagerAdapter;
import com.example.jeason.swipe.Interface.Fragment2ActivityCommunicator;
import com.example.jeason.swipe.R;

public class MainActivity extends AppCompatActivity implements Fragment2ActivityCommunicator {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DETAIL_KEY = "TITLE";
    private TextView textViewFromRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        ViewPager mViewPager = findViewById(R.id.home_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void dataFromFragmentBackToActivity(View view) {
//        Intent detailIntent = new Intent(this, ArticleDetailActivity.class);
        textViewFromRecyclerView = (TextView) view;
        switchToDetailActivity();
    }

    private void switchToDetailActivity() {
        if (textViewFromRecyclerView != null) {
            String textFromRecyclerViewText = textViewFromRecyclerView.getText().toString();
            String[] mantraTitleList = getResources().getStringArray(R.array.mantra_title);
            for (String text : mantraTitleList) {
                if (textFromRecyclerViewText.equals(text)) {
                    Intent mantraDetailIntent = new Intent(this, MantraDetailActivity.class);
                    mantraDetailIntent.putExtra(DETAIL_KEY, textFromRecyclerViewText);
                    startActivity(mantraDetailIntent);
                }
            }

            String[] articleTitleList = getResources().getStringArray(R.array.article_title);
            for (String text : articleTitleList) {
                if (textFromRecyclerViewText.equals(text)) {
                    Intent articleDetailIntent = new Intent(this, ArticleDetailActivity.class);
                    articleDetailIntent.putExtra(DETAIL_KEY, textFromRecyclerViewText);
                    startActivity(articleDetailIntent);
                }
            }


            String[] videoTitleList = getResources().getStringArray(R.array.video_title);
            for (String text : videoTitleList) {
                if (textFromRecyclerViewText.equals(text)) {
                    Intent articleDetailIntent = new Intent(this, VideoDetailActivity.class);
                    articleDetailIntent.putExtra(DETAIL_KEY, textFromRecyclerViewText);
                    startActivity(articleDetailIntent);
                }
            }
        }

    }
}
