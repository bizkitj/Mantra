package com.example.jeason.swipe.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jeason.swipe.R;

import static com.example.jeason.swipe.activity.MainActivity.DETAIL_KEY;

public class ArticleDetailActivity extends AppCompatActivity {
    private static final String TAG = ArticleDetailActivity.class.getSimpleName();
    private String itemTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity_detail);
        Intent intent = getIntent();
        itemTitle = intent.getStringExtra(DETAIL_KEY);

        TextView toolBarTitle = findViewById(R.id.toolBarTextView);
        toolBarTitle.setText(itemTitle);
        Toolbar toolbar = findViewById(R.id.detailViewToolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        loadArticle();
    }

    private void loadArticle() {
        TextView articleTextView = findViewById(R.id.textViewInDetailView);

        switch (itemTitle) {

            case "略论明心见性":
                articleTextView.setText(Html.fromHtml(getString(R.string.略论明心见性)));
                break;
            case "佛法修证心要问答集":
                articleTextView.setText(Html.fromHtml(getString(R.string.佛法修证心要问答集)));
                break;
            case "大手印浅释":
                articleTextView.setText(Html.fromHtml(getString(R.string.大手印浅释)));
                break;
            case "净土指归":
                articleTextView.setText(Html.fromHtml(getString(R.string.净土指归)));
                break;
            case "禅净密互融互通的修法":
                articleTextView.setText(Html.fromHtml(getString(R.string.禅净密互融互通的修法)));
                break;
            case "成佛的诀窍":
                articleTextView.setText(Html.fromHtml(getString(R.string.成佛的诀窍)));
                break;
            case "略论禅宗":
                articleTextView.setText(Html.fromHtml(getString(R.string.略论禅宗)));
                break;
            case "人人皆当成佛":
                articleTextView.setText(Html.fromHtml(getString(R.string.人人皆当成佛)));
                break;
            case "法偈":
                articleTextView.setText(Html.fromHtml(getString(R.string.法偈)));
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //refer to https://developer.android.com/training/search/setup
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
