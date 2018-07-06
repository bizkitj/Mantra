package com.example.jeason.swipe.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.example.jeason.swipe.DummyData.DatabaseTable;
import com.example.jeason.swipe.R;

public class ArticleSearchResultsActivity extends AppCompatActivity {

    private static final String TAG = ArticleSearchResultsActivity.class.getCanonicalName();
    private DatabaseTable db;
    private Cursor cursor;
    private ListView searchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_search_results);

        TextView toolBarTitle = findViewById(R.id.toolBarTextView);
        toolBarTitle.setText(R.string.search_result);
        Toolbar toolbar = findViewById(R.id.articleSearchResultViewToolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        searchResultListView = findViewById(R.id.searchResult);
        db = new DatabaseTable(this);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            cursor = db.getWordMatches(query);
            //process Cursor and display results
            CursorAdapter adapter = new ArticleSearchAdapter(this, cursor);
            searchResultListView.setAdapter(adapter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseEverything();
        Log.v(TAG, "onStop");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        releaseEverything();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            releaseEverything();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void releaseEverything() {
        if (cursor != null) {
            cursor.close();
        }
    }

    private class ArticleSearchAdapter extends CursorAdapter {

        ArticleSearchAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            return layoutInflater.inflate(R.layout.article_search_reslut_row_item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView word = view.findViewById(R.id.word);
            TextView wordDefinition = view.findViewById(R.id.word_definition);
            int columnIndexWord = cursor.getColumnIndex(DatabaseTable.COL_WORD);
            int columnIndexWordDefinition = cursor.getColumnIndex(DatabaseTable.COL_DEFINITION);
            word.setText(cursor.getString(columnIndexWord));
            wordDefinition.setText(cursor.getString(columnIndexWordDefinition));
        }
    }
}
