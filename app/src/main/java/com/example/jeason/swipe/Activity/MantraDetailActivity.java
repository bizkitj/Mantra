package com.example.jeason.swipe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jeason.swipe.R;

import static com.example.jeason.swipe.Activity.MainActivity.DETAIL_KEY;

public class MantraDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_detail);
        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra(DETAIL_KEY);
        TextView toolBarTitle = findViewById(R.id.toolBarTextView);
        toolBarTitle.setText(itemTitle);
        Toolbar toolbar = findViewById(R.id.MantraDetailActivityToolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        TextView textView = findViewById(R.id.textView_mantraDetailActivity);
        textView.setText(itemTitle);
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
